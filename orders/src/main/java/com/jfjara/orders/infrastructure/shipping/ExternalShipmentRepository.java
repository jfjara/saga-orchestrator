package com.jfjara.orders.infrastructure.shipping;

import com.jfjara.orders.domain.model.ShipmentRequestDTO;
import com.jfjara.orders.domain.model.ShipmentStatusDTO;
import com.jfjara.orders.domain.model.exception.ShipmentException;
import com.jfjara.orders.domain.service.ShipmentRepository;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class ExternalShipmentRepository implements ShipmentRepository {

    private final ShipmentClient client;

    public ExternalShipmentRepository(final ShipmentClient client) {
        this.client = client;
    }

    @Override
    public Optional<ShipmentStatusDTO> ship(final String userId, final String productId) throws ShipmentException {
        try {
            return Optional.of(this.client.shipping(new ShipmentRequestDTO(productId)));
        } catch (final Exception exception) {
            throw new ShipmentException(ShipmentStatusDTO.FAILED, exception.getMessage());
        }
    }

}
