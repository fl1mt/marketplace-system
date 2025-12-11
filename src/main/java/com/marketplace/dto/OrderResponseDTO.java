package com.marketplace.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderResponseDTO {
    private UUID id;
    private UserResponseDTO userResponseDTO;
    private DeliveryAddressResponseDTO deliveryAddressResponseDTO;
    private LocalDate deliveryDate;
    private BigDecimal total;
    private List<OrderItemResponseDTO> items;
    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime updatedAt;

    public OrderResponseDTO() {
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setUserResponseDTO(UserResponseDTO userResponseDTO) {
        this.userResponseDTO = userResponseDTO;
    }

    public UserResponseDTO getUserResponseDTO() {
        return userResponseDTO;
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

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
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

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
