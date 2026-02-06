package com.marketplace.events;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record DeliveryCalculatedEvent(
        UUID orderId,
        BigDecimal deliveryPrice,
        LocalDate deliveryDate
) {}