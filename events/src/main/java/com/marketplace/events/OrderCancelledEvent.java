package com.marketplace.events;

import java.util.UUID;

public record OrderCancelledEvent(UUID orderId) {
}
