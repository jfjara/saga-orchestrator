package com.jfjara.orders.infrastructure.shipping;

import com.jfjara.orders.domain.model.ShipmentRequestDTO;
import com.jfjara.orders.domain.model.ShipmentStatusDTO;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import jakarta.inject.Singleton;

import java.net.URL;

@Singleton
public class ShipmentClient {

    private final String host;
    private final String shipPath;
    private final BlockingHttpClient blockingClient;

    public ShipmentClient(@Value("${app.orders.shipment.host}") String host,
                          @Value("${app.orders.shipment.ship.path}") String shipPath) throws Exception {
        this.host = host;
        this.shipPath = shipPath;
        final HttpClient client = HttpClient.create(new URL(this.host));
        this.blockingClient = client.toBlocking();
    }

    public ShipmentStatusDTO shipping(final ShipmentRequestDTO product) {
        final HttpRequest<ShipmentRequestDTO> request = HttpRequest.POST(shipPath, product);
        final HttpResponse<ShipmentStatusDTO> response = this.blockingClient.exchange(request, ShipmentStatusDTO.class);
        return response.body();
    }

}
