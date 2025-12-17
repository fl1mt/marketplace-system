package com.marketplace.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderItemResponseDTO {
    private UUID id;
    private ProductSnapshotDTO productSnapshotDTO;
    private Integer quantity;
    private BigDecimal priceAtPurchase;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setProductSnapshotDTO(ProductSnapshotDTO productSnapshotDTO) {
        this.productSnapshotDTO = productSnapshotDTO;
    }

    public ProductSnapshotDTO getProductSnapshotDTO() {
        return productSnapshotDTO;
    }
}
