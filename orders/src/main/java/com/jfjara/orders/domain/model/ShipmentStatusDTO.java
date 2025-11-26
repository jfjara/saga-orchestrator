package com.jfjara.orders.domain.model;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public enum ShipmentStatusDTO {
    OK, OUT_OF_STOCK, FAILED
}
