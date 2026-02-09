package com.marketplace.logisticservice.service;

import com.marketplace.events.DeliveryCalculatedEvent;
import com.marketplace.events.OrderCreatedEvent;
import com.marketplace.logisticservice.delivery.Delivery;
import com.marketplace.logisticservice.delivery.DeliveryStatus;
import com.marketplace.logisticservice.kafka.DeliveryCalculatedProducer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DeliveryService {

    private final DeliveryCalculatedProducer deliveryCalculatedProducer;

    public DeliveryService(DeliveryCalculatedProducer deliveryCalculatedProducer) {
        this.deliveryCalculatedProducer = deliveryCalculatedProducer;
    }

    public void calculateAndSend(OrderCreatedEvent event) {
        // временная "Затычка" доставки заказа со статичными данными
        Delivery delivery = new Delivery();
        delivery.setDeliveryStatus(DeliveryStatus.CREATED);

        BigDecimal deliveryPrice = BigDecimal.valueOf(500);
        LocalDate deliveryDate = LocalDate.now().plusDays(3);
        DeliveryCalculatedEvent result =
                new DeliveryCalculatedEvent(
                        event.orderId(),
                        deliveryPrice,
                        deliveryDate
                );

        delivery.setOrderId(event.orderId());
        delivery.setShippingCost(deliveryPrice);
        delivery.setDeliveryDate(deliveryDate);
        delivery.setDeliveryStatus(DeliveryStatus.CONFIRMED);

        deliveryCalculatedProducer.send(result);
    }
}

