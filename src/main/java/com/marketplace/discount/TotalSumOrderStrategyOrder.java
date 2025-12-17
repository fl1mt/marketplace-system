package com.marketplace.discount;

import com.marketplace.entity.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

@Component
public class TotalSumOrderStrategyOrder implements OrderDiscountStrategy {
    private static final TreeMap<BigDecimal, BigDecimal> THRESHOLD_DISCOUNTS = new TreeMap<>();
    static {
        THRESHOLD_DISCOUNTS.put(BigDecimal.valueOf(1000), new BigDecimal("0.03"));
        THRESHOLD_DISCOUNTS.put(BigDecimal.valueOf(5000), new BigDecimal("0.05"));
        THRESHOLD_DISCOUNTS.put(BigDecimal.valueOf(10000), new BigDecimal("0.10"));
    }

    @Override
    public BigDecimal calculateOrderDiscount(Order order) {
        BigDecimal subtotal = order.getSubtotal();

        Map.Entry<BigDecimal, BigDecimal> discountEntry = THRESHOLD_DISCOUNTS.floorEntry(subtotal);

        if (discountEntry == null) { return BigDecimal.ZERO; }
        if (subtotal.compareTo(discountEntry.getKey()) <= 0) { return BigDecimal.ZERO; }

        BigDecimal discountAmount = subtotal.multiply(discountEntry.getValue())
                .setScale(2, RoundingMode.HALF_UP);

        return discountAmount;
    }
}
