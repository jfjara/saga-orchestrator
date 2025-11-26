package com.jfjara.orders.domain.model.exception;

import com.jfjara.orders.domain.model.PaymentStatusDTO;

public class PaymentException extends Exception {

    private final PaymentStatusDTO status;
    private final String message;

    public PaymentException(final PaymentStatusDTO status, final String message) {
        this.status = status;
        this.message = message;
    }

    public PaymentStatusDTO getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
