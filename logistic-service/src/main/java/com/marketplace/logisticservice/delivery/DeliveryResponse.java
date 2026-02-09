package com.marketplace.logisticservice.delivery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DeliveryResponse {
    private Long id;
    private UUID orderId;
    private BigDecimal shippingCost;
    private LocalDate deliveryDate;
    private DeliveryStatus deliveryStatus;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }
}
