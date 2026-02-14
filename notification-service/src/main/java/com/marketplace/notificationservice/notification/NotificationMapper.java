package com.marketplace.notificationservice.notification;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponse toResponseDTO (Notification notification);
    List<NotificationResponse> toListResponseDTO (List<Notification> notifications);
}
