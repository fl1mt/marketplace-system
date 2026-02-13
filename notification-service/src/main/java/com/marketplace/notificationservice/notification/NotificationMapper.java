package com.marketplace.notificationservice.notification;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponse toResponseDTO (Notification notification);
}
