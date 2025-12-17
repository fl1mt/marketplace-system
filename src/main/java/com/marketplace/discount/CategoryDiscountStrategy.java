package com.marketplace.discount;

import com.marketplace.entity.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Component
public class CategoryDiscountStrategy implements ProductDiscountStrategy{
    private static final Map<String, BigDecimal> CATEGORY_DISCOUNTS = Map.of(
      "ELECTRONICS", new BigDecimal("0.15"),
            "FOOD", new BigDecimal("0.05")
    );

    @Override
    public BigDecimal calculateProductDiscount(Product product) {
        if (product.getCategory() == null || product.getBasePrice() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal discountFactor = CATEGORY_DISCOUNTS.getOrDefault(product.getCategory().name(), BigDecimal.ZERO);
        return product.getBasePrice().multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
    }
}
