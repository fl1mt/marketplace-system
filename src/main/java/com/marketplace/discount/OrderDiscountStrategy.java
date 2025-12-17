package com.marketplace.discount;

import com.marketplace.entity.Order;

import java.math.BigDecimal;

public interface OrderDiscountStrategy {
    BigDecimal calculateOrderDiscount(Order order);
}
