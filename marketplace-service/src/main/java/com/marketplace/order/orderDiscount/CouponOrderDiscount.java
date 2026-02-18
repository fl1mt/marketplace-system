package com.marketplace.order.orderDiscount;

import com.marketplace.order.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CouponOrderDiscount implements OrderDiscountStrategy {
    @Override
    public BigDecimal calculateOrderDiscount(Order order) {
        // promocode discount
        return BigDecimal.ZERO;
    }
}
