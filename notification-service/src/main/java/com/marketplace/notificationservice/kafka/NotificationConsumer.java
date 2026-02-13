package com.marketplace.notificationservice.kafka;

import com.marketplace.events.OrderStatusChangedEvent;
import com.marketplace.notificationservice.notification.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "order-status-changed",
            groupId = "notification-service"
    )
    public void consume(OrderStatusChangedEvent event) {
        notificationService.sendNotification(event);
    }
}
