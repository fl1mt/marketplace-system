package com.marketplace.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderResponseDTO {
    private UUID id;
    private UserPublicDTO userPublicDTO;
    private DeliveryAddressResponseDTO deliveryAddressResponseDTO;
    private LocalDate deliveryDate;
    private BigDecimal subtotal;
    private BigDecimal finalTotal;
    private List<OrderItemResponseDTO> items;
    private LocalDateTime createdAt;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setDeliveryAddressResponseDTO(DeliveryAddressResponseDTO deliveryAddressResponseDTO) {
        this.deliveryAddressResponseDTO = deliveryAddressResponseDTO;
    }

    public DeliveryAddressResponseDTO getDeliveryAddressResponseDTO() {
        return deliveryAddressResponseDTO;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setFinalTotal(BigDecimal finalTotal) {
        this.finalTotal = finalTotal;
    }

    public BigDecimal getFinalTotal() {
        return finalTotal;
    }

    public void setItems(List<OrderItemResponseDTO> items) {
        this.items = items;
    }

    public List<OrderItemResponseDTO> getItems() {
        return items;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setUserPublicDTO(UserPublicDTO userPublicDTO) {
        this.userPublicDTO = userPublicDTO;
    }

    public UserPublicDTO getUserPublicDTO() {
        return userPublicDTO;
    }
}
