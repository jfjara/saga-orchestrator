package com.jfjara.orders.domain.service;

import com.jfjara.orders.domain.model.OrderDTO;
import com.jfjara.orders.domain.model.OrderStatusDTO;

import java.util.Optional;

public interface OrderRepository {

    String create(final OrderDTO productDTO);
    void updateStatus(final String orderId, final OrderStatusDTO status);
    Optional<OrderDTO> find(final String userId, final String orderId);
}
