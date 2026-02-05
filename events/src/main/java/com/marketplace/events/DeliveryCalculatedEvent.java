package com.marketplace.events;

import java.math.BigDecimal;

public record DeliveryCalculatedEvent(
        Long orderId,
        int deliveryDays,
        BigDecimal deliveryPrice
) {}

