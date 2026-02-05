package com.marketplace.events;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DeliveryCalculatedEvent(
        Long orderId,
        int deliveryDays,
        BigDecimal deliveryPrice,
        LocalDate deliveryDate
) {}

