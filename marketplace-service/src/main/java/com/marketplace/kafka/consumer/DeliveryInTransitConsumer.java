package com.marketplace.kafka.consumer;

import com.marketplace.events.DeliveryInTransitEvent;
import com.marketplace.kafka.producer.OrderEventProducer;
import com.marketplace.order.Order;
import com.marketplace.order.OrderStatus;
import com.marketplace.order.OrdersRepository;
import com.marketplace.user.auth.DataAuthService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class DeliveryInTransitConsumer {

    private final OrdersRepository ordersRepository;
    private final DataAuthService dataAuthService;
    private final OrderEventProducer producer;


    public DeliveryInTransitConsumer(OrdersRepository ordersRepository, DataAuthService dataAuthService, OrderEventProducer producer) {
        this.ordersRepository = ordersRepository;
        this.dataAuthService = dataAuthService;
        this.producer = producer;
    }

    @KafkaListener(
            topics = "delivery-in-transit",
            groupId = "marketplace-service"
    )
    public void consume(DeliveryInTransitEvent event) {

        Order order = dataAuthService.checkOrder(event.orderId());

        if (order.getOrderStatus() == OrderStatus.IN_TRANSIT) {
            return;
        }

        order.setOrderStatus(OrderStatus.IN_TRANSIT);
        ordersRepository.save(order);

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        producer.sendOrderChangedStatusEvent(order);
                    }
                }
        );
    }
}
