package com.marketplace.events;

import com.marketplace.order.OrderStatus;

import java.util.UUID;

public record OrderStatusChangedEvent (
        OrderStatus status,
        UUID userId,
        UUID orderId,
        String email,
        String phone
){ }
