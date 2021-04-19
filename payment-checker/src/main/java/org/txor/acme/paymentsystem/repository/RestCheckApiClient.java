package org.txor.acme.paymentsystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.txor.acme.paymentsystem.domain.CheckApiClient;
import org.txor.acme.paymentsystem.domain.Payment;
import org.txor.acme.paymentsystem.domain.PaymentStatus;

@Component
public class RestCheckApiClient implements CheckApiClient {

    private final RestTemplate restTemplate;
    private final String host;
    private final String port;

    @Autowired
    RestCheckApiClient(RestTemplateBuilder builder,
                       @Value("${payment-checker.check.host}") String host,
                       @Value("${payment-checker.check.port}") String port) {
        this.restTemplate = builder.build();
        this.host = host;
        this.port = port;
    }

    @Override
    public PaymentStatus checkPayment(Payment any) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.postForEntity("http://" + host + ":" + port + "/check", new HttpEntity<>("{}", headers), String.class);
        HttpStatus status = response.getStatusCode();
        if (status.is2xxSuccessful()) {
            return PaymentStatus.OK;
        } else {
            return PaymentStatus.KO;
        }
    }

}
