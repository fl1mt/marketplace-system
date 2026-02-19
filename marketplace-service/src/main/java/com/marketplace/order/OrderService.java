package com.marketplace.order;

import com.marketplace.delivery.DeliveryAddress;
import com.marketplace.errors.BadRequestException;
import com.marketplace.events.OrderStatus;
import com.marketplace.kafka.producer.OrderEventProducer;
import com.marketplace.orderItem.OrderItem;
import com.marketplace.orderItem.OrderItemRequestDTO;
import com.marketplace.orderItem.OrderItemResponseDTO;
import com.marketplace.orderItem.OrderItemMapper;
import com.marketplace.orderItem.OrderItemsRepository;
import com.marketplace.product.Product;
import com.marketplace.product.stock.StockService;
import com.marketplace.user.auth.DataAuthService;
import com.marketplace.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final DataAuthService dataAuthService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrdersRepository ordersRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final OrderEventProducer orderEventProducer;
    private final StockService stockService;

    public OrderService(DataAuthService dataAuthService, OrderMapper orderMapper,
                        OrderItemMapper orderItemMapper, OrdersRepository ordersRepository,
                        OrderItemsRepository orderItemsRepository, OrderEventProducer orderEventProducer, StockService stockService) {
        this.dataAuthService = dataAuthService;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.ordersRepository = ordersRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.orderEventProducer = orderEventProducer;
        this.stockService = stockService;
    }

    @Transactional
    public OrderResponseDTO createUsersOrder(UUID userId, OrderRequestDTO orderRequestDTO){
        if (orderRequestDTO.getItems() == null || orderRequestDTO.getItems().isEmpty()) {
            throw new BadRequestException("Order must contain at least one item");
        }

        User user = dataAuthService.checkUsersId(userId);
        DeliveryAddress deliveryAddress = dataAuthService.checkUsersDeliveryAddress(orderRequestDTO.getDeliveryAddressId(), userId);

        Order newOrder = orderMapper.toEntity(orderRequestDTO);

        createDraftOrder(user, deliveryAddress, newOrder);
        ordersRepository.save(newOrder);

        List<OrderItemCommand> orderItemCommands = orderRequestDTO.getItems().stream()
                .map(dto -> new OrderItemCommand(dto.getProductId(), dto.getQuantity()))
                .toList();

        List<OrderItem> orderItems = createOrderItems(newOrder, orderItemCommands);
        orderItemsRepository.saveAll(orderItems);

        stockService.reserveStock(orderItems);
        requestDelivery(newOrder);
        return buildResponse(newOrder, orderItems);
    }

    public List<OrderResponseDTO> getUsersOrders(UUID userId, boolean onlyActiveOrders){
        User user = dataAuthService.checkUsersId(userId);

        List<Order> orders = onlyActiveOrders ? ordersRepository.findByUserIdAndOrderStatusNotIn(user.getId(),
                List.of(OrderStatus.COMPLETED, OrderStatus.CANCELLED_BY_USER, OrderStatus.CANCELLED)) :
        ordersRepository.findAllByUserId(user.getId());

        if(orders.isEmpty()){
            return List.of();
        }

        List<UUID> ordersIds = orders.stream()
                .map(Order::getId)
                .toList();

        List<OrderItem> orderItems = orderItemsRepository.findByOrderIdsWithProducts(ordersIds);

        Map<UUID, List<OrderItem>> itemsByOrderId = orderItems.stream()
                .collect(Collectors.groupingBy(item -> item.getOrder().getId()));

        List<OrderResponseDTO> responseDTOs = new ArrayList<>();

        for (Order order: orders){
            OrderResponseDTO orderResponseDTO = orderMapper.toResponseDTO(order);

            List<OrderItem> currentItems = itemsByOrderId.getOrDefault(order.getId(), List.of());
            List<OrderItemResponseDTO> itemsDTO = orderItemMapper.toOrderItemsListDTO(currentItems);

            orderResponseDTO.setItems(itemsDTO);
            responseDTOs.add(orderResponseDTO);
        }

        return responseDTOs;
    }

    @Transactional
    public void cancelOrderByUser(UUID userId, UUID orderId){
        Order order = dataAuthService.checkOrdersAffiliation(orderId, userId);

        switch (order.getOrderStatus()){
            case DELIVERY_CONFIRMED, WAITING_FOR_RECEIVE -> {
                stockService.returnStockWhenOrderCanceled(orderItemsRepository.findByOrderIdWithProducts(orderId));
                TransactionSynchronizationManager.registerSynchronization(
                        new TransactionSynchronization() {
                            @Override
                            public void afterCommit() {
                                orderEventProducer.sendOrderCancelledEvent(order);
                            }
                        }
                );
            }
            case IN_TRANSIT -> throw new BadRequestException
                    ("The order is on the way. You can cancel it when it arrives.");
        };
        order.setOrderStatus(OrderStatus.CANCELLED_BY_USER);
        ordersRepository.save(order);

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        orderEventProducer.sendOrderChangedStatusEvent(order);
                    }
                }
        );
    }

    @Transactional
    public void receiveOrderByUser(UUID userId, UUID orderId) {
        Order order = dataAuthService.checkOrdersAffiliation(orderId, userId);

        if(!order.getOrderStatus().equals(OrderStatus.WAITING_FOR_RECEIVE))
        {throw new BadRequestException("The order must have is waiting to receive status.");}

        order.setOrderStatus(OrderStatus.COMPLETED);
        ordersRepository.save(order);

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        orderEventProducer.sendOrderChangedStatusEvent(order);
                    }
                }
        );
    }

    @Transactional
    public void createOrderFromCart(UUID userId, UUID deliveryAddressId, Map<UUID, Integer> cart) {
        Order order = new Order();
        createDraftOrder(dataAuthService.checkUsersId(userId), dataAuthService.checkUsersDeliveryAddress(deliveryAddressId,
                userId), order);
        ordersRepository.save(order);

        List<OrderItemCommand> orderItemCommands = cart.entrySet().stream()
                .map(item -> new OrderItemCommand(item.getKey(), item.getValue()))
                .toList();

        List<OrderItem> orderItems = createOrderItems(order, orderItemCommands);
        orderItemsRepository.saveAll(orderItems);

        stockService.reserveStock(orderItems);
        requestDelivery(order);
    }

    private void createDraftOrder(User user, DeliveryAddress address, Order order) {
        order.setUser(user);
        order.setAddress(address);
        order.setSubtotal(BigDecimal.ZERO);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setFinalTotal(BigDecimal.ZERO);
        order.setOrderStatus(OrderStatus.CREATED);
    }

    private List<OrderItem> createOrderItems(Order order, List<OrderItemCommand> items) {

        List<OrderItem> result = new ArrayList<>();

        for (OrderItemCommand orderItem : items) {
            Product product = dataAuthService.checkProduct(orderItem.productId());
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(orderItem.quantity());
            item.setPriceAtPurchase(product.getFinalPrice());

            result.add(item);
        }

        return result;
    }

    private OrderResponseDTO buildResponse(Order order, List<OrderItem> items) {
        OrderResponseDTO dto = orderMapper.toResponseDTO(order);
        dto.setItems(orderItemMapper.toOrderItemsListDTO(items));
        return dto;
    }

    public void requestDelivery(Order order) {
        if(order.getOrderStatus() != OrderStatus.CREATED){
            throw new BadRequestException("The order must have created status.");
        }
        order.setOrderStatus(OrderStatus.DELIVERY_REQUESTED);
        ordersRepository.save(order);

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        orderEventProducer.sendOrderCreatedEvent(order);
                    }
                }
        );
    }
}
