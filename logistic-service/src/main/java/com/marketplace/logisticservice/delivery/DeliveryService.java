package com.marketplace.logisticservice.delivery;

import com.marketplace.events.DeliveryCalculatedEvent;
import com.marketplace.events.OrderCreatedEvent;
import com.marketplace.logisticservice.kafka.DeliveryCalculatedProducer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryCalculatedProducer deliveryCalculatedProducer;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    public DeliveryService(DeliveryCalculatedProducer deliveryCalculatedProducer, DeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper) {
        this.deliveryCalculatedProducer = deliveryCalculatedProducer;
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
    }

    public List<DeliveryResponse> getDeliveries(){
        return deliveryMapper.toResponseDtoList(deliveryRepository.findAll());
    }

    @Transactional
    public void calculateAndSend(OrderCreatedEvent event) {

        Delivery delivery = new Delivery();
        delivery.setOrderId(event.orderId());
        delivery.setDeliveryStatus(DeliveryStatus.CREATED);

        deliveryRepository.save(delivery);

        // временная заглушка
        BigDecimal deliveryPrice = BigDecimal.valueOf(500);
        LocalDate deliveryDate = LocalDate.now().plusDays(3);

        delivery.setShippingCost(deliveryPrice);
        delivery.setDeliveryDate(deliveryDate);
        delivery.setDeliveryStatus(DeliveryStatus.CONFIRMED);

        deliveryRepository.save(delivery);

        DeliveryCalculatedEvent result =
                new DeliveryCalculatedEvent(
                        event.orderId(),
                        deliveryPrice,
                        deliveryDate
                );

        deliveryCalculatedProducer.send(result);
    }

}

