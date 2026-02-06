package com.marketplace.events;

import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        UUID userId,
        String city,
        String street,
        String index
) {}