package com.marketplace.discount;

import com.marketplace.entity.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CouponOrderDiscountStrategy implements OrderDiscountStrategy {
    @Override
    public BigDecimal calculateOrderDiscount(Order order) {
        String promoCode = order.getPromoCode();
        // promocode discount
        return BigDecimal.ZERO;
    }
}
