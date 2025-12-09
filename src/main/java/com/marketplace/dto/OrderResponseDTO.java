package com.marketplace.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}
