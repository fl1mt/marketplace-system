package com.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class ProductRequestDTO {
    @NotBlank(message = "Product name is required!")
    private String name;
    @NotBlank(message = "Product description is required!")
    private String description;
    @NotBlank(message = "Product price is required!")
    private BigDecimal price;

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

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
