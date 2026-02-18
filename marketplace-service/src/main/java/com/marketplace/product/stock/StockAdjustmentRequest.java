package com.marketplace.product.stock;

import jakarta.validation.constraints.NotNull;

public record StockAdjustmentRequest (
        @NotNull int amount
){ }
