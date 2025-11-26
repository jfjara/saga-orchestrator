package com.jfjara.orders.infrastructure.payment;

import com.jfjara.orders.domain.model.PaymentRequestDTO;
import com.jfjara.orders.domain.model.PaymentStatusDTO;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import jakarta.inject.Singleton;

import java.net.URL;

@Singleton
public class PaymentClient {

    private final String host;
    private final String paymentPath;
    private final String refundPath;

    private final BlockingHttpClient blockingClient;

    public PaymentClient(@Value("${app.orders.payment.host}") String host,
                         @Value("${app.orders.payment.payment.path}") String paymentPath,
                         @Value("${app.orders.payment.refund.path}") String refundPath) throws Exception {
        this.host = host;
        this.paymentPath = paymentPath;
        this.refundPath = refundPath;
        final HttpClient client = HttpClient.create(new URL(this.host));
        this.blockingClient = client.toBlocking();
    }

    public PaymentStatusDTO pay(final PaymentRequestDTO dto) {
        final HttpRequest<PaymentRequestDTO> request = HttpRequest.POST(paymentPath, dto);
        final HttpResponse<PaymentStatusDTO> response = this.blockingClient.exchange(request, PaymentStatusDTO.class);
        return response.body();
    }

    public PaymentStatusDTO refund(final PaymentRequestDTO dto) {
        final HttpRequest<PaymentRequestDTO> request = HttpRequest.POST(refundPath, dto);
        final HttpResponse<PaymentStatusDTO> response = this.blockingClient.exchange(request, PaymentStatusDTO.class);
        return response.body();
    }

}
