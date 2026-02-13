package com.marketplace.kafka.consumer;

import com.marketplace.errors.NotFoundException;
import com.marketplace.events.DeliveryInTransitEvent;
import com.marketplace.order.Order;
import com.marketplace.order.OrderStatus;
import com.marketplace.order.OrdersRepository;
import com.marketplace.user.auth.DataAuthService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DeliveryInTransitConsumer {

    private final OrdersRepository ordersRepository;
    private final DataAuthService dataAuthService;

    public DeliveryInTransitConsumer(OrdersRepository ordersRepository, DataAuthService dataAuthService) {
        this.ordersRepository = ordersRepository;
        this.dataAuthService = dataAuthService;
    }

    @KafkaListener(
            topics = "delivery-in-transit",
            groupId = "marketplace-service"
    )
    public void consume(DeliveryInTransitEvent event) {

        Order order = dataAuthService.checkOrder(event.orderId());

        if (order.getOrderStatus() == OrderStatus.IN_TRANSIT) {
            return;
        }

        order.setOrderStatus(OrderStatus.IN_TRANSIT);
        ordersRepository.save(order);
    }
}
