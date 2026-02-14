package com.marketplace.kafka.consumer;

import com.marketplace.events.DeliveryDeliveredEvent;
import com.marketplace.kafka.producer.OrderEventProducer;
import com.marketplace.order.Order;
import com.marketplace.events.OrderStatus;
import com.marketplace.order.OrdersRepository;
import com.marketplace.user.auth.DataAuthService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDate;

@Component
public class DeliveryDeliveredConsumer {
    private final OrdersRepository ordersRepository;
    private final DataAuthService dataAuthService;
    private final OrderEventProducer producer;


    public DeliveryDeliveredConsumer(OrdersRepository ordersRepository, DataAuthService dataAuthService, OrderEventProducer producer) {
        this.ordersRepository = ordersRepository;
        this.dataAuthService = dataAuthService;
        this.producer = producer;
    }


    @Transactional
    @KafkaListener(
            topics = "delivery-delivered",
            groupId = "marketplace-service"
    )
    public void consume(DeliveryDeliveredEvent event) {

        Order order = dataAuthService.checkOrder(event.orderId());

        order.setOrderStatus(OrderStatus.WAITING_FOR_RECEIVE);
        order.setDeliveryDate(LocalDate.now());
        ordersRepository.save(order);

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        System.out.println("DELIVERY DELIVERED SEND NOTIFICATION");
                        producer.sendOrderChangedStatusEvent(order);
                    }
                }
        );
    }
}
