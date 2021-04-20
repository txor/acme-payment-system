package org.txor.acme.paymentsystem.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.txor.acme.paymentsystem.domain.InvalidPaymentException;
import org.txor.acme.paymentsystem.domain.Payment;

@Component
public class PaymentConverter {

    private final ObjectMapper objectMapper;

    @Autowired
    public PaymentConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String convert(Payment payment) throws InvalidPaymentException {
        try {
            return objectMapper.writeValueAsString(payment);
        } catch (JsonProcessingException e) {
            throw new InvalidPaymentException(e);
        }
    }
}
