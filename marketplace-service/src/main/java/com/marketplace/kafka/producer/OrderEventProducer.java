package com.marketplace.kafka.producer;
import com.marketplace.events.OrderCreatedEvent;
import com.marketplace.order.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
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
}
