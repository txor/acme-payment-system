package org.txor.acme.paymentsystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.txor.acme.paymentsystem.domain.Payment;
import org.txor.acme.paymentsystem.domain.UpdateApiClient;

@Component
public class RestUpdateApiClient implements UpdateApiClient {

    private final RestTemplate restTemplate;
    private final PaymentConverter paymentConverter;
    private final String host;
    private final String port;

    @Autowired
    RestUpdateApiClient(RestTemplateBuilder builder,
                        PaymentConverter paymentConverter,
                        @Value("${payment-checker.update.host}") String host,
                        @Value("${payment-checker.update.port}") String port) {
        this.restTemplate = builder.build();
        this.paymentConverter = paymentConverter;
        this.host = host;
        this.port = port;
    }

    @Override
    public void updatePayment(Payment payment) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String request = paymentConverter.convert(payment);
        restTemplate.postForEntity("http://" + host + ":" + port + "/update", new HttpEntity<>(request, headers), String.class);
    }

}
