package com.jfjara.orders.domain.model;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record OrderDTO(String orderId, String userId, ProductDTO product, OrderStatusDTO status) {

    public OrderDTO update(String orderId, OrderStatusDTO status) {
        return new OrderDTO(orderId, this.userId, this.product, status);
    }
}
