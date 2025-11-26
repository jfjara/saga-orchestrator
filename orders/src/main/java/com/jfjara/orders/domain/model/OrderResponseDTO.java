package com.jfjara.orders.domain.model;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record OrderResponseDTO(String orderId, OrderStatusDTO status) {
}
