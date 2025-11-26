package com.jfjara.orders.boot;

import com.jfjara.orders.application.service.OrderService;
import com.jfjara.orders.domain.orchestrator.OrdersOrchestrator;
import com.jfjara.orders.domain.service.CacheRepository;
import com.jfjara.orders.domain.service.OrderRepository;
import com.jfjara.orders.domain.service.PaymentRepository;
import com.jfjara.orders.domain.service.ShipmentRepository;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

@Factory
public class ServiceFactory {

    @Singleton
    public OrderRepository orderService(final CacheRepository cacheRepository) {
        return new OrderService(cacheRepository);
    }

    @Singleton
    public OrdersOrchestrator ordersOrchestrator(final OrderRepository orderRepository,
                                                 final PaymentRepository paymentRepository,
                                                 final ShipmentRepository shipmentRepository) {
        return new OrdersOrchestrator(orderRepository, paymentRepository, shipmentRepository);
    }

}
