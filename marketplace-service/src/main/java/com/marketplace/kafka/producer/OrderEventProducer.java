package com.marketplace.kafka.producer;
import com.marketplace.events.OrderCreatedEvent;
import com.marketplace.events.OrderStatusChangedEvent;
import com.marketplace.order.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    public OrderEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(Order order) {

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getUser().getId(),
                order.getAddress().getCity(),
                String.join(" ", order.getAddress().getStreet(), order.getAddress().getHouse(), order.getAddress().getApartment()),
                order.getAddress().getIndex()
        );

        kafkaTemplate.send(
                "order-created",
                order.getId().toString(),
                event
        );
    }

    public void sendOrderChangedStatusEvent(Order order){

        OrderStatusChangedEvent event = new OrderStatusChangedEvent(
                order.getOrderStatus(),
                order.getUser().getId(),
                order.getId(),
                order.getUser().getEmail(),
                order.getUser().getPhoneNumber()
        );

        kafkaTemplate.send(
                "order-status-changed",
                order.getId().toString(),
                event
        );
    }
}
