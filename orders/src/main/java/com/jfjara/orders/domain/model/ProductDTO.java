package com.jfjara.orders.domain.model;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ProductDTO(String id, Double price) {
}
