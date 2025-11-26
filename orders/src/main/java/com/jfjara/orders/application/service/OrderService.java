package com.jfjara.orders.application.service;

import com.jfjara.orders.domain.model.OrderDTO;
import com.jfjara.orders.domain.service.CacheRepository;
import com.jfjara.orders.domain.service.OrderRepository;
import com.jfjara.orders.domain.model.OrderStatusDTO;

import java.util.Optional;
import java.util.UUID;

public class OrderService implements OrderRepository {

    private final CacheRepository cacheRepository;

    public OrderService(final CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    @Override
    public String create(final OrderDTO orderDTO) {
        var orderUpdated = orderDTO.update(UUID.randomUUID().toString(), OrderStatusDTO.CREATED);
        this.cacheRepository.addOrder(orderUpdated);
        return orderUpdated.orderId();
    }

    @Override
    public void updateStatus(final String orderId, final OrderStatusDTO status) {
        this.cacheRepository.updateOrder(orderId, status);
    }

    @Override
    public Optional<OrderDTO> find(final String userId, final String orderId) {
        return this.cacheRepository.getOrder(userId, orderId);
    }

}
