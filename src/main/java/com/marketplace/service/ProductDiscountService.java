package com.marketplace.service;

import com.marketplace.discount.ProductDiscountStrategy;
import com.marketplace.entity.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProductDiscountService {

    private final List<ProductDiscountStrategy> strategies;
    public ProductDiscountService(List<ProductDiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    public BigDecimal calculateFinalPrice(Product product) {
        BigDecimal basePrice = product.getBasePrice();
        BigDecimal totalDiscountAmount = BigDecimal.ZERO;

        for (ProductDiscountStrategy strategy : strategies) {
            BigDecimal discount = strategy.calculateProductDiscount(product);
            totalDiscountAmount = totalDiscountAmount.add(discount);
        }

        BigDecimal finalPrice = basePrice.subtract(totalDiscountAmount)
                .setScale(2, RoundingMode.HALF_UP);

        if (finalPrice.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }

        return finalPrice;
    }
}