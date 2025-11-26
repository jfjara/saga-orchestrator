package com.jfjara.orders.domain.service;

import com.jfjara.orders.domain.model.PaymentStatusDTO;
import com.jfjara.orders.domain.model.exception.PaymentException;

import java.util.Optional;

public interface PaymentRepository {

    Optional<PaymentStatusDTO> pay(final String userId, final Double price) throws PaymentException;
    Optional<PaymentStatusDTO> refund(final String userId, final Double price) throws PaymentException;
}
