package com.marketplace.service;

import com.marketplace.discount.OrderDiscountStrategy;
import com.marketplace.entity.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DiscountStrategyService {

    private final List<OrderDiscountStrategy> strategies;

    public DiscountStrategyService(List<OrderDiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    public BigDecimal calculateTotalOrderDiscount(Order order) {
        BigDecimal totalDiscount = BigDecimal.ZERO;

        for (OrderDiscountStrategy strategy : strategies) {
            BigDecimal discount = strategy.calculateOrderDiscount(order);
            totalDiscount = totalDiscount.add(discount);
        }

        if (totalDiscount.compareTo(order.getSubtotal()) > 0) {
            return order.getSubtotal();
        }

        return totalDiscount.setScale(2, RoundingMode.HALF_UP);
    }
}
