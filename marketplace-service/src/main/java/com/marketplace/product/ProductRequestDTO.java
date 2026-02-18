package com.marketplace.product;

import com.marketplace.product.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductRequestDTO {
    @NotBlank(message = "Product name is required!")
    private String name;
    @NotBlank(message = "Product description is required!")
    private String description;
    @NotBlank(message = "Product category is required!")
    private ProductType category;
    @NotBlank(message = "Product discountPercentage is required!")
    private Integer discountPercentage;
    @NotBlank(message = "Product inventoryStock is required!")
    private Integer inventoryStock;
    @NotBlank(message = "Product basePrice is required!")
    private BigDecimal basePrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCategory(ProductType category) {
        this.category = category;
    }

    public ProductType getCategory() {
        return category;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setInventoryStock(Integer inventoryStock) {
        this.inventoryStock = inventoryStock;
    }

    public Integer getInventoryStock() {
        return inventoryStock;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }
}
