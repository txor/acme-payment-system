package org.txor.acme.paymentsystem.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentConverter {

    private final ObjectMapper objectMapper;

    public PaymentConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Payment convert(String payment) throws InvalidPaymentException {
        try {
            return objectMapper.readValue(payment, Payment.class);
        } catch (JsonProcessingException e) {
            throw new InvalidPaymentException(e);
        }
    }

    public String convert(Payment payment) throws InvalidPaymentException {
        try {
            return objectMapper.writeValueAsString(payment);
        } catch (JsonProcessingException e) {
            throw new InvalidPaymentException(e);
        }
    }
}
