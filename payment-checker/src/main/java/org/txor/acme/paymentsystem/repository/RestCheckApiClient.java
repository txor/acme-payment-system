package org.txor.acme.paymentsystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.txor.acme.paymentsystem.domain.CheckApiClient;
import org.txor.acme.paymentsystem.domain.InvalidPaymentException;
import org.txor.acme.paymentsystem.domain.Payment;

@Component
public class RestCheckApiClient implements CheckApiClient {

    private final RestTemplate restTemplate;
    private final PaymentConverter paymentConverter;
    private final String host;
    private final String port;

    @Autowired
    RestCheckApiClient(RestTemplateBuilder builder,
                       PaymentConverter paymentConverter,
                       @Value("${payment-checker.check.host}") String host,
                       @Value("${payment-checker.check.port}") String port) {
        this.restTemplate = builder.build();
        this.paymentConverter = paymentConverter;
        this.host = host;
        this.port = port;
    }

    @Override
    public void checkPayment(Payment payment) throws InvalidPaymentException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String request = paymentConverter.convert(payment);
        restTemplate.postForEntity("http://" + host + ":" + port + "/payment", new HttpEntity<>(request, headers), String.class);
    }

}
