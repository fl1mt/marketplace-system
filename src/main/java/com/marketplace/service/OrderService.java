package com.marketplace.service;

import com.marketplace.dto.OrderItemRequestDTO;
import com.marketplace.dto.OrderItemResponseDTO;
import com.marketplace.dto.OrderRequestDTO;
import com.marketplace.dto.OrderResponseDTO;
import com.marketplace.entity.*;
import com.marketplace.mapper.OrderItemMapper;
import com.marketplace.mapper.OrderMapper;
import com.marketplace.repository.OrderItemsRepository;
import com.marketplace.repository.OrdersRepository;
import com.marketplace.repository.ProductsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ProductsRepository productsRepository;
    private final DiscountStrategyService discountStrategyService;

    public OrderService(DataAuthService dataAuthService, OrderMapper orderMapper,
                        OrderItemMapper orderItemMapper, OrdersRepository ordersRepository,
                        OrderItemsRepository orderItemsRepository, ProductsRepository productsRepository, DiscountStrategyService discountStrategyService) {
        this.dataAuthService = dataAuthService;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.ordersRepository = ordersRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.productsRepository = productsRepository;
        this.discountStrategyService = discountStrategyService;
    }

    @Transactional
    public OrderResponseDTO createUsersOrder(UUID userId,
                                             OrderRequestDTO orderRequestDTO){
        User user = dataAuthService.checkUsersId(userId);
        DeliveryAddress deliveryAddress = dataAuthService.checkUsersDeliveryAddress(orderRequestDTO.getDeliveryAddressId(), userId);

        Order newOrder = orderMapper.toEntity(orderRequestDTO);
        newOrder.setUser(user);
        newOrder.setAddress(deliveryAddress);
        newOrder.setSubtotal(BigDecimal.ZERO);
        newOrder.setFinalTotal(BigDecimal.ZERO);
        newOrder.setDiscountAmount(BigDecimal.ZERO);
        Order savedOrder = ordersRepository.save(newOrder);

        // deliveryDate + shipping cost

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemDTO : orderRequestDTO.getItems()){
            Product product = productsRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found!" + itemDTO.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());

            orderItem.setPriceAtPurchase(product.getFinalPrice());
            orderItemsRepository.save(orderItem);

            BigDecimal itemTotal = product.getFinalPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        savedOrder.setSubtotal(totalAmount);

        BigDecimal discountAmount = discountStrategyService.calculateTotalOrderDiscount(savedOrder);
        savedOrder.setDiscountAmount(discountAmount);
        BigDecimal totalSumWithDiscount = savedOrder.getSubtotal().subtract(discountAmount);
        savedOrder.setFinalTotal(totalSumWithDiscount);
        ordersRepository.save(savedOrder);

        List<OrderItem> items = orderItemsRepository.findByOrderIdWithProducts(savedOrder.getId());
        List<OrderItemResponseDTO> orderItemsDTO = orderItemMapper.toOrderItemsListDTO(items);

        OrderResponseDTO orderResponseDTO = orderMapper.toResponseDTO(savedOrder);
        orderResponseDTO.setItems(orderItemsDTO);
        return orderResponseDTO;
    }

    public List<OrderResponseDTO> getUsersOrders(UUID userId){
        User user = dataAuthService.checkUsersId(userId);
        List<Order> orders = ordersRepository.findAllByUserId(user.getId());

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
}
