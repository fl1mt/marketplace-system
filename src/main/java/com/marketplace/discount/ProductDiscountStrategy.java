package com.marketplace.discount;
import com.marketplace.entity.Product;
import java.math.BigDecimal;

public interface ProductDiscountStrategy {
    BigDecimal calculateProductDiscount(Product product);
}
