package com.marketplace.notificationservice.notification;
import com.marketplace.notificationservice.notification.enums.NotificationType;
import jakarta.persistence.*;
import com.marketplace.notificationservice.notification.enums.NotificationChannel;
import com.marketplace.notificationservice.notification.enums.NotificationStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@EntityListeners(AuditingEntityListener.class)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private UUID userId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Column(nullable = false)
    private String title;
    @Column
    private String message;
    @Column(nullable = false)
    private String recipient;
    @CreatedDate
    @Column
    private LocalDateTime createdAt;
    @Column
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
