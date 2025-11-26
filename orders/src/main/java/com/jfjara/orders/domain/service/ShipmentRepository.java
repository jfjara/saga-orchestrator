package com.jfjara.orders.domain.service;

import com.jfjara.orders.domain.model.ShipmentStatusDTO;
import com.jfjara.orders.domain.model.exception.ShipmentException;

import java.util.Optional;

public interface ShipmentRepository {

    Optional<ShipmentStatusDTO> ship(final String userId, final String productId) throws ShipmentException;
}
