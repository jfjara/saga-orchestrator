package com.jfjara.orders.domain.model;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public enum PaymentStatusDTO {
    OK, FAILED, WITHOUT_BALANCE
}
