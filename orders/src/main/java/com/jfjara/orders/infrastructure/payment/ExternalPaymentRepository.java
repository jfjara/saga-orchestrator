package com.jfjara.orders.infrastructure.payment;

import com.jfjara.orders.domain.model.PaymentRequestDTO;
import com.jfjara.orders.domain.model.PaymentStatusDTO;
import com.jfjara.orders.domain.model.exception.PaymentException;
import com.jfjara.orders.domain.service.PaymentRepository;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class ExternalPaymentRepository implements PaymentRepository {

    private final PaymentClient paymentClient;

    public ExternalPaymentRepository(final PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @Override
    public Optional<PaymentStatusDTO> pay(final String userId, final Double price) throws PaymentException {
        final PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(userId, price);

        try {
            return Optional.of(this.paymentClient.pay(paymentRequestDTO));
        } catch (final Exception exception) {
            throw new PaymentException(PaymentStatusDTO.FAILED, exception.getMessage());
        }
   }

    @Override
    public Optional<PaymentStatusDTO> refund(final String userId, final Double price) throws PaymentException {
        final PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(userId, price);

        try {
            return Optional.of(this.paymentClient.refund(paymentRequestDTO));
        } catch (final Exception exception) {
            throw new PaymentException(PaymentStatusDTO.FAILED, exception.getMessage());
        }
    }
}
