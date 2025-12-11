package com.marketplace.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class OrderRequestDTO {
    @NotBlank(message = "Delivery address is required!")
    private UUID deliveryAddressId;
    private LocalDate deliveryDate;
    private List<OrderItemRequestDTO> items;
    public void setDeliveryAddressId(UUID deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public UUID getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setItems(List<OrderItemRequestDTO> items) {
        this.items = items;
    }

    public List<OrderItemRequestDTO> getItems() {
        return items;
    }
}
