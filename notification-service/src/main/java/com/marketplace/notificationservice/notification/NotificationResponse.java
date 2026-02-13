package com.marketplace.notificationservice.notification;

import com.marketplace.notificationservice.notification.enums.NotificationChannel;
import com.marketplace.notificationservice.notification.enums.NotificationStatus;
import com.marketplace.notificationservice.notification.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationResponse {
    private Long id;
    private UUID userId;
    private NotificationType type;
    private NotificationChannel channel;
    private NotificationStatus status;
    private String title;
    private String message;
    private String recipient;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public NotificationType getType() {
        return type;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}
