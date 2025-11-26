package com.jfjara.orders.domain.model;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public enum OrderStatusDTO {
    CREATED, PENDING, SHIPPED, OUT_OF_STOCK, CANCELLED, PAID, PAYMENT_FAILED, SHIPMENT_FAILED
}
