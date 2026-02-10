package com.marketplace.kafka.consumer;

import com.marketplace.errors.NotFoundException;
import com.marketplace.events.DeliveryInTransitEvent;
import com.marketplace.order.Order;
import com.marketplace.order.OrderStatus;
import com.marketplace.order.OrdersRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DeliveryInTransitConsumer {

    private final OrdersRepository ordersRepository;

    public DeliveryInTransitConsumer(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @KafkaListener(
            topics = "delivery-in-transit",
            groupId = "marketplace-service"
    )
    public void consume(DeliveryInTransitEvent event) {

        Order order = ordersRepository.findById(event.orderId())
                .orElseThrow(() ->
                        new NotFoundException(
                                "Order not found for in-transit event. orderId=" + event.orderId()
                        )
                );

        if (order.getOrderStatus() == OrderStatus.IN_TRANSIT) {
            return;
        }

        order.setOrderStatus(OrderStatus.IN_TRANSIT);
        ordersRepository.save(order);
    }
}
