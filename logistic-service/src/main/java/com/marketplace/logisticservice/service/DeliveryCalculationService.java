package com.marketplace.logisticservice.service;

import com.marketplace.events.DeliveryCalculatedEvent;
import com.marketplace.events.OrderCreatedEvent;
import com.marketplace.logisticservice.kafka.DeliveryCalculatedProducer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DeliveryCalculationService {

    private final DeliveryCalculatedProducer deliveryCalculatedProducer;

    public DeliveryCalculationService(DeliveryCalculatedProducer deliveryCalculatedProducer) {
        this.deliveryCalculatedProducer = deliveryCalculatedProducer;
    }

    public void calculateAndSend(OrderCreatedEvent event) {
        // временная "Затычка" доставки заказа со статичными данными
        BigDecimal deliveryPrice = BigDecimal.valueOf(500);
        LocalDate deliveryDate = LocalDate.now().plusDays(3);
        DeliveryCalculatedEvent result =
                new DeliveryCalculatedEvent(
                        event.orderId(),
                        deliveryPrice,
                        deliveryDate
                );

        deliveryCalculatedProducer.send(result);
    }
}

