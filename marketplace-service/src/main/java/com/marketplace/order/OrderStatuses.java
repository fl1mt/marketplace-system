package com.marketplace.order;

public enum OrderStatuses {
    CREATED,
    DELIVERY_REQUESTED,
    DELIVERY_CONFIRMED,
    IN_TRANSIT,
    WAITING_FOR_RECEIVE,
    COMPLETED,
    CANCELED
}
