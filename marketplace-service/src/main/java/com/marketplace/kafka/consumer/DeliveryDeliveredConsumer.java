package com.marketplace.kafka.consumer;

import com.marketplace.errors.NotFoundException;
import com.marketplace.events.DeliveryDeliveredEvent;
import com.marketplace.order.Order;
import com.marketplace.order.OrderStatus;
import com.marketplace.order.OrdersRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DeliveryDeliveredConsumer {
    private final OrdersRepository ordersRepository;

    public DeliveryDeliveredConsumer(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @KafkaListener(
            topics = "delivery-delivered",
            groupId = "marketplace-service"
    )
    public void consume(DeliveryDeliveredEvent event) {

        Order order = ordersRepository.findById(event.orderId())
                .orElseThrow(() ->
                        new NotFoundException(
                                "Order not found for changing delivery status. orderId="
                                        + event.orderId()
                        )
                );
        order.setOrderStatus(OrderStatus.WAITING_FOR_RECEIVE);
        order.setDeliveryDate(LocalDate.now());
        // add notification service
        ordersRepository.save(order);
    }
}
