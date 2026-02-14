package com.marketplace.kafka.consumer;

import com.marketplace.events.DeliveryCalculatedEvent;
import com.marketplace.events.OrderStatus;
import com.marketplace.kafka.producer.OrderEventProducer;
import com.marketplace.order.*;
import com.marketplace.orderItem.OrderItem;
import com.marketplace.user.auth.DataAuthService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Component
public class DeliveryCalculatedConsumer {

    private final OrdersRepository ordersRepository;
    private final OrderPricingService orderPricingService;
    private final OrderEventProducer producer;
    private final DataAuthService dataAuthService;

    public DeliveryCalculatedConsumer(OrdersRepository ordersRepository,
                                      OrderPricingService orderPricingService,
                                      OrderEventProducer producer, DataAuthService dataAuthService) {
        this.ordersRepository = ordersRepository;
        this.orderPricingService = orderPricingService;
        this.producer = producer;
        this.dataAuthService = dataAuthService;
    }

    @Transactional
    @KafkaListener(
            topics = "delivery-calculated",
            groupId = "marketplace-service"
    )
    public void consume(DeliveryCalculatedEvent event) {

        Order order = dataAuthService.checkOrder(event.orderId());

        if (order.getOrderStatus() != OrderStatus.DELIVERY_REQUESTED) {
            return;
        }

        order.setDeliveryDate(event.deliveryDate());
        order.setShippingCost(event.deliveryPrice());

        List<OrderItem> orderItems = dataAuthService.checkOrderItemsByOrder(order.getId());

        orderPricingService.calculateTotals(order, orderItems);

        order.setOrderStatus(OrderStatus.DELIVERY_CONFIRMED);
        ordersRepository.save(order);

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        System.out.println("DELIVERY CONFIRMED SEND NOTIFICATION");
                        producer.sendOrderChangedStatusEvent(order);
                    }
                }
        );
    }
}

