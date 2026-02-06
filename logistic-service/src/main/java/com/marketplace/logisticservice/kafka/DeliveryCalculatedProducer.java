package com.marketplace.logisticservice.kafka;

import com.marketplace.events.DeliveryCalculatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DeliveryCalculatedProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DeliveryCalculatedProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(DeliveryCalculatedEvent event) {
        kafkaTemplate.send(
                "delivery-calculated",
                event.orderId().toString(),
                event
        );
    }
}