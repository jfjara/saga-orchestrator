package com.jfjara.orders.infrastructure.storage;

import com.jfjara.orders.domain.model.OrderDTO;
import com.jfjara.orders.domain.model.OrderStatusDTO;
import com.jfjara.orders.domain.service.CacheRepository;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class MemoryCacheOrder implements CacheRepository {

    private final Map<String, Map<String, OrderDTO>> cache = new HashMap<>();

    public void addOrder(final OrderDTO orderDTO) {
        final Map<String, OrderDTO> orders = cache.computeIfAbsent(orderDTO.userId(), k -> new HashMap<>());
        orders.put(orderDTO.orderId(), orderDTO);
        this.cache.put(orderDTO.userId(), orders);
    }

    public Optional<OrderDTO> getOrder(final String userId, final String orderId) {
        return Optional.ofNullable(this.cache.getOrDefault(userId, new HashMap<>()).getOrDefault(orderId, null));
    }

    @Override
    public void updateOrder(final String orderId, final OrderStatusDTO status) {
        for (final Map<String, OrderDTO> userOrders : this.cache.values()) {
            final OrderDTO updated = userOrders.computeIfPresent(orderId, (id, currenOrder) ->
                    currenOrder.update(orderId, status));
            if (updated != null) {
                return;
            }
        }

    }

}
