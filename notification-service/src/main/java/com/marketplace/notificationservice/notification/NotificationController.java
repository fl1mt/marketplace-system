package com.marketplace.notificationservice.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/v1/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<NotificationResponse> getAllNotifications(){
        return null; // заглушка
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotification(@PathVariable Long id){
        return null; // заглушка
    }
}
