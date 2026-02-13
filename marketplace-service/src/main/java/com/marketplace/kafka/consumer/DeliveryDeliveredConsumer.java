package com.marketplace.kafka.consumer;

import com.marketplace.events.DeliveryDeliveredEvent;
import com.marketplace.order.Order;
import com.marketplace.order.OrderStatus;
import com.marketplace.order.OrdersRepository;
import com.marketplace.user.auth.DataAuthService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DeliveryDeliveredConsumer {
    private final OrdersRepository ordersRepository;
    private final DataAuthService dataAuthService;

    public DeliveryDeliveredConsumer(OrdersRepository ordersRepository, DataAuthService dataAuthService) {
        this.ordersRepository = ordersRepository;
        this.dataAuthService = dataAuthService;
    }

    @KafkaListener(
            topics = "delivery-delivered",
            groupId = "marketplace-service"
    )
    public void consume(DeliveryDeliveredEvent event) {

        Order order = dataAuthService.checkOrder(event.orderId());

        order.setOrderStatus(OrderStatus.WAITING_FOR_RECEIVE);
        order.setDeliveryDate(LocalDate.now());
        // add notification service
        ordersRepository.save(order);
    }
}
