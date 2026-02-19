package com.marketplace.order;

import java.util.UUID;

public record OrderItemCommand(
        UUID productId,
        int quantity
) {
}
