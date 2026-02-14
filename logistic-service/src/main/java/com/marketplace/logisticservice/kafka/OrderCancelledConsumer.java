package com.marketplace.logisticservice.kafka;

import com.marketplace.events.OrderCancelledEvent;
import com.marketplace.logisticservice.delivery.Delivery;
import com.marketplace.logisticservice.delivery.DeliveryRepository;
import com.marketplace.logisticservice.delivery.DeliveryStatus;
import com.marketplace.logisticservice.errors.NotFoundException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderCancelledConsumer {
    private final DeliveryRepository deliveryRepository;

    public OrderCancelledConsumer(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Transactional
    @KafkaListener(
            topics = "order-cancelled",
            groupId = "logistics-service"
    )
    public void consume(OrderCancelledEvent event) {
        Delivery delivery = deliveryRepository.findByOrderId(event.orderId());
        if (delivery == null){
            throw new NotFoundException("Delivery by order not found! order id: " + event.orderId());
        }
        delivery.setDeliveryStatus(DeliveryStatus.CANCELLED);
        deliveryRepository.save(delivery);
    }
}
