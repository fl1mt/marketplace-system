package com.marketplace.notificationservice.notification;

import com.marketplace.errors.BadRequestException;
import com.marketplace.events.OrderStatusChangedEvent;
import com.marketplace.notificationservice.notification.enums.NotificationChannel;
import com.marketplace.notificationservice.notification.enums.NotificationStatus;
import com.marketplace.notificationservice.notification.enums.NotificationType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void sendNotification(OrderStatusChangedEvent event){
        Notification notification = new Notification();
        NotificationType type;

        try {
            type = NotificationType.valueOf(event.status().name());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Unsupported notification type: " + event.status());
        }

        notification.setUserId(event.userId());
        notification.setType(type);
        notification.setChannel(resolveChannel(notification.getType()));
        notification.setRecipient(getNotificationRecipient(notification.getChannel(), event));
        notification.setStatus(NotificationStatus.PENDING);
        notification.setMessage(setMessageNotification(notification.getType(), event.orderId()));
        notification.setTitle("Заказ №" + event.orderId()); //временно затычка в виде UUID

        notificationRepository.save(notification);

        // позже добавлю затычку в видео 10 секунд, якобы уведомления отправляется
    }

    private NotificationChannel resolveChannel(NotificationType type) {
        return switch (type) {
            case WAITING_FOR_RECEIVE -> NotificationChannel.SMS;
            default -> NotificationChannel.EMAIL;
        };
    }

    private String getNotificationRecipient(NotificationChannel notificationChannel, OrderStatusChangedEvent event){
        return switch (notificationChannel){
            case EMAIL -> event.email();
            default -> event.phone();
        };
    }

    private String setMessageNotification(NotificationType notificationType, UUID orderId){
        return switch (notificationType){
            case DELIVERY_CONFIRMED -> "Здравствуйте! Заказ №" + orderId + "успешно подтвержден. :)";
            case IN_TRANSIT -> "Заказ уже в пути!";
            case WAITING_FOR_RECEIVE -> "Заказ прибыл и готов к получению :)";
            case COMPLETED -> "Заказ был успешно получен. Пожалуйста, оцените товары";
            case CANCELED -> "Заказ №" + orderId + "был отменён.";
        };
    }
}
