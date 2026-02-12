package com.marketplace.kafka.consumer;

import com.marketplace.events.DeliveryCalculatedEvent;
import com.marketplace.order.*;
import com.marketplace.orderItem.OrderItem;
import com.marketplace.user.auth.DataAuthService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DeliveryCalculatedConsumer {

    private final OrdersRepository ordersRepository;
    private final OrderPricingService orderPricingService;
    private final DataAuthService dataAuthService;
    private final StockService stockService;

    public DeliveryCalculatedConsumer(OrdersRepository ordersRepository,
                                      OrderPricingService orderPricingService, DataAuthService dataAuthService, StockService stockService) {
        this.ordersRepository = ordersRepository;
        this.orderPricingService = orderPricingService;
        this.dataAuthService = dataAuthService;
        this.stockService = stockService;
    }

    @Transactional
    @KafkaListener(
            topics = "delivery-calculated",
            groupId = "marketplace-service"
    )
    public void consume(DeliveryCalculatedEvent event) {

        Order order = dataAuthService.checkOrder(event.orderId());

        if (order.getOrderStatus() != OrderStatus.DELIVERY_REQUESTED) {
            return;
        }

        order.setDeliveryDate(event.deliveryDate());
        order.setShippingCost(event.deliveryPrice());

        List<OrderItem> orderItems = dataAuthService.checkOrderItemsByOrder(order.getId());

        orderPricingService.calculateTotals(order, orderItems);

        order.setOrderStatus(OrderStatus.DELIVERY_CONFIRMED);
        ordersRepository.save(order);
    }
}

