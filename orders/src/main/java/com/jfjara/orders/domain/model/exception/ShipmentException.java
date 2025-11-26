package com.jfjara.orders.domain.model.exception;

import com.jfjara.orders.domain.model.ShipmentStatusDTO;

public class ShipmentException extends Exception {

    private final ShipmentStatusDTO status;
    private final String message;

    public ShipmentException(final ShipmentStatusDTO status, final String message) {
        this.status = status;
        this.message = message;
    }

    public ShipmentStatusDTO getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
