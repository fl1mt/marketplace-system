package com.marketplace.logisticservice.kafka;
import com.marketplace.events.OrderCreatedEvent;
import com.marketplace.logisticservice.service.DeliveryCalculationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    private final DeliveryCalculationService calculationService;

    public OrderCreatedConsumer(DeliveryCalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @KafkaListener(
            topics = "order-created",
            groupId = "logistics-service"
    )
    public void consume(OrderCreatedEvent event) {
        calculationService.calculateAndSend(event);
    }
}
