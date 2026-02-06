package com.marketplace.kafka.consumer;

import com.marketplace.errors.NotFoundException;
import com.marketplace.events.DeliveryCalculatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import com.marketplace.order.Order;
import com.marketplace.order.OrdersRepository;
import org.springframework.stereotype.Component;

@Component
public class DeliveryCalculatedConsumer {

    private final OrdersRepository ordersRepository;

    public DeliveryCalculatedConsumer(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @KafkaListener(
            topics = "delivery-calculated",
            groupId = "marketplace-service"
    )
    public void consume(DeliveryCalculatedEvent event) {

        Order order = ordersRepository.findById(event.getOrderId())
                .orElseThrow(() ->
                        new NotFoundException(
                                "Order not found for delivery calculation. orderId=" + event.getOrderId()
                        )
                );

        order.setDeliveryDate(event.getDeliveryDate());
        order.setShippingCost(event.getDeliveryPrice());

        ordersRepository.save(order);
    }
}

