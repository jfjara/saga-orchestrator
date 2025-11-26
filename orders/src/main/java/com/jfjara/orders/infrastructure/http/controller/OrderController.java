package com.jfjara.orders.infrastructure.http.controller;

import com.jfjara.orders.domain.model.OrderDTO;
import com.jfjara.orders.domain.model.OrderResponseDTO;
import com.jfjara.orders.domain.orchestrator.OrdersOrchestrator;
import com.jfjara.orders.domain.service.OrderRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@Controller("/orders")
@ExecuteOn(TaskExecutors.IO)
public class OrderController {

    private final OrdersOrchestrator ordersOrchestrator;
    private final OrderRepository orderRepository;

    public OrderController(final OrdersOrchestrator ordersOrchestrator, final OrderRepository orderRepository) {
        this.ordersOrchestrator = ordersOrchestrator;
        this.orderRepository = orderRepository;
    }

    @Post("/add")
    public HttpResponse<OrderResponseDTO> createOrder(final @Body OrderDTO orderDTO) {
        if (!this.isValid(orderDTO)) {
            return HttpResponse.badRequest();
        }
        return HttpResponse.ok(this.ordersOrchestrator.manage(orderDTO));
    }

    private boolean isValid(final OrderDTO orderDTO) {
        return orderDTO != null && orderDTO.product() != null &&
                orderDTO.product().id() != null && orderDTO.product().price() != null &&
                orderDTO.userId() != null;
    }

    @Get("/{userId}/{orderId}")
    public HttpResponse<OrderDTO> get(final @PathVariable String userId, final @PathVariable String orderId) {
        var order = this.orderRepository.find(userId, orderId).orElseThrow();
        return HttpResponse.ok(order);
    }

}
