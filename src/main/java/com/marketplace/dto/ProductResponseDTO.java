package com.marketplace.dto;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
