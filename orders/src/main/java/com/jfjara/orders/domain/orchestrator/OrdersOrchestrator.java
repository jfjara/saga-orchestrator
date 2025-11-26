package com.jfjara.orders.domain.orchestrator;

import com.jfjara.orders.domain.model.OrderDTO;
import com.jfjara.orders.domain.model.OrderResponseDTO;
import com.jfjara.orders.domain.model.OrderStatusDTO;
import com.jfjara.orders.domain.model.PaymentStatusDTO;
import com.jfjara.orders.domain.model.ShipmentStatusDTO;
import com.jfjara.orders.domain.model.exception.PaymentException;
import com.jfjara.orders.domain.model.exception.ShipmentException;
import com.jfjara.orders.domain.service.OrderRepository;
import com.jfjara.orders.domain.service.PaymentRepository;
import com.jfjara.orders.domain.service.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrdersOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(OrdersOrchestrator.class);

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ShipmentRepository shipmentRepository;

    public OrdersOrchestrator(final OrderRepository orderRepository,
                              final PaymentRepository paymentRepository,
                              final ShipmentRepository shipmentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.shipmentRepository = shipmentRepository;
    }

    public OrderResponseDTO manage(final OrderDTO orderDTO) {
        log.info(":: Start order :: {}", orderDTO);

        final String orderId = this.orderRepository.create(orderDTO);
        log.info(":: Order created with id {}::", orderId);

        try {
            this.orderRepository.updateStatus(orderId, OrderStatusDTO.PENDING);

            final PaymentStatusDTO paymentResult = this.executePayment(orderDTO);

            if (this.isPaymentFailed(paymentResult)) {
                log.error(":: ERROR: Payment not successful. Status {} ::",  paymentResult);
                return this.failOrder(orderId, OrderStatusDTO.PAYMENT_FAILED);
            }
            log.info(":: Payment OK :: {}", paymentResult);

            this.orderRepository.updateStatus(orderId, OrderStatusDTO.PAID);

            final ShipmentStatusDTO shipmentResult = this.executeShipment(orderId, orderDTO);

            if (shipmentResult == ShipmentStatusDTO.OUT_OF_STOCK) {
                log.error(":: ERROR: Shipment not successful. Status {} ::", shipmentResult);
                this.refund(orderDTO);
                log.info(":: Refund OK :: {}", orderDTO);
                return this.failOrder(orderId, OrderStatusDTO.OUT_OF_STOCK);
            }

            if (shipmentResult == ShipmentStatusDTO.FAILED) {
                log.error(":: ERROR: Shipment not successful. Status {} ::", shipmentResult);
                this.refund(orderDTO);
                log.info(":: Refund OK :: {}", orderDTO);
                return this.failOrder(orderId, OrderStatusDTO.CANCELLED);
            }

            log.info(":: Shipment OK :: {}", shipmentResult);

            this.orderRepository.updateStatus(orderId, OrderStatusDTO.SHIPPED);
            return new OrderResponseDTO(orderId, OrderStatusDTO.SHIPPED);

        } catch (final PaymentException e) {
            log.error(":: ERROR: Payment not successful. Status {}. Message exception {} ::", e.getStatus(), e.getMessage());
            return this.failOrder(orderId, OrderStatusDTO.PAYMENT_FAILED);
        } catch (final ShipmentException e) {
            log.error(":: ERROR: Shipment not successful. Status {}. Message exception {} ::", e.getStatus(), e.getMessage());
            this.refund(orderDTO);
            log.info(":: Refund OK :: {}", orderDTO);
            return this.failOrder(orderId, OrderStatusDTO.SHIPMENT_FAILED);
        }
    }

    private PaymentStatusDTO executePayment(final OrderDTO orderDTO) throws PaymentException {
        return this.paymentRepository
                .pay(orderDTO.userId(), orderDTO.product().price())
                .orElse(PaymentStatusDTO.FAILED);
    }

    private ShipmentStatusDTO executeShipment(final String orderId, final OrderDTO orderDTO) throws ShipmentException {
        return this.shipmentRepository
                .ship(orderId, orderDTO.product().id())
                .orElse(ShipmentStatusDTO.FAILED);
    }

    private boolean isPaymentFailed(final PaymentStatusDTO status) {
        return status == PaymentStatusDTO.WITHOUT_BALANCE
                || status == PaymentStatusDTO.FAILED;
    }

    private OrderResponseDTO failOrder(String orderId, OrderStatusDTO status) {
        this.orderRepository.updateStatus(orderId, status);
        return new OrderResponseDTO(orderId, status);
    }

    private void refund(OrderDTO orderDTO) {
        try {
            this.paymentRepository.refund(orderDTO.userId(), orderDTO.product().price());
        } catch (final PaymentException exception) {

        }
    }


}
