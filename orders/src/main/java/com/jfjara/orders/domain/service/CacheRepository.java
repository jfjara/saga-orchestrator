package com.jfjara.orders.domain.service;

import com.jfjara.orders.domain.model.OrderDTO;
import com.jfjara.orders.domain.model.OrderStatusDTO;

import java.util.Optional;

public interface CacheRepository {

    void addOrder(final OrderDTO orderDTO);
    Optional<OrderDTO> getOrder(final String userId, final String orderId);
    void updateOrder(final String orderId, OrderStatusDTO status);

}
