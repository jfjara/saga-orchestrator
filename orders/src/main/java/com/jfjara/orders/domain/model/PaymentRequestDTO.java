package com.jfjara.orders.domain.model;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record PaymentRequestDTO(String userId, Double price) {
}
