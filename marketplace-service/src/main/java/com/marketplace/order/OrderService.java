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
    public OrderResponseDTO createUsersOrder(UUID userId,
                                             OrderRequestDTO orderRequestDTO){
        validateOrderRequest(orderRequestDTO);

        User user = dataAuthService.checkUsersId(userId);
        DeliveryAddress deliveryAddress = dataAuthService.checkUsersDeliveryAddress(orderRequestDTO.getDeliveryAddressId(), userId);

        Order newOrder = createDraftOrder(user, deliveryAddress, orderRequestDTO);
        ordersRepository.save(newOrder);

        List<OrderItem> orderItems = createOrderItems(newOrder, orderRequestDTO.getItems());
        orderItemsRepository.saveAll(orderItems);

        stockService.reserveStock(orderItems);

        requestDelivery(newOrder);

        return buildResponse(newOrder, orderItems);
    }

    public List<OrderResponseDTO> getUsersOrders(UUID userId, boolean onlyActiveOrders){
        User user = dataAuthService.checkUsersId(userId);

        List<Order> orders = onlyActiveOrders ? ordersRepository.findByUserIdAndOrderStatusNotIn(userId,
                List.of(OrderStatus.COMPLETED, OrderStatus.CANCELED)) :
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
    public void receiveOrderByUser(UUID userId, UUID orderId) {
        Order order = dataAuthService.checkOrdersAffiliation(orderId, userId);

        if(!order.getOrderStatus().equals(OrderStatus.WAITING_FOR_RECEIVE))
        {throw new BadRequestException("The order must have is waiting to receive status.");}

        order.setOrderStatus(OrderStatus.COMPLETED);
        ordersRepository.save(order);
    }

    private void validateOrderRequest(OrderRequestDTO request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BadRequestException("Order must contain at least one item");
        }
    }

    private Order createDraftOrder(User user, DeliveryAddress address, OrderRequestDTO request) {
        Order order = orderMapper.toEntity(request);
        order.setUser(user);
        order.setAddress(address);
        order.setSubtotal(BigDecimal.ZERO);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setFinalTotal(BigDecimal.ZERO);
        order.setOrderStatus(OrderStatus.CREATED);
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, List<OrderItemRequestDTO> items) {

        List<OrderItem> result = new ArrayList<>();

        for (OrderItemRequestDTO itemDTO : items) {
            Product product = dataAuthService.checkProduct(itemDTO.getProductId());
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
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

    private void requestDelivery(Order order) {
        order.setOrderStatus(OrderStatus.DELIVERY_REQUESTED);
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
