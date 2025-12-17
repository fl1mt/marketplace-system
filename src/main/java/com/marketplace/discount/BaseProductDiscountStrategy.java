package com.marketplace.discount;

import com.marketplace.entity.Product;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class BaseProductDiscountStrategy implements ProductDiscountStrategy{
    private static final int DECIMAL_PLACES = 2;
    @Override
    public BigDecimal calculateProductDiscount(Product product) {
        BigDecimal basePrice = product.getBasePrice();
        Integer discountPercentage = product.getDiscountPercentage();

        if (discountPercentage == null || discountPercentage <= 0 || basePrice == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal discountFactor = new BigDecimal(discountPercentage).divide(BigDecimal.valueOf(100),
                DECIMAL_PLACES,
                RoundingMode.HALF_UP);
        return basePrice.multiply(discountFactor).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
    }
}
