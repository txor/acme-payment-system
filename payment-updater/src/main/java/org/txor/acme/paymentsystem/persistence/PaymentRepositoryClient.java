package org.txor.acme.paymentsystem.persistence;

import org.springframework.stereotype.Component;
import org.txor.acme.paymentsystem.domain.Payment;
import org.txor.acme.paymentsystem.domain.PaymentRepository;

@Component
public class PaymentRepositoryClient implements PaymentRepository {

    @Override
    public void savePayment(Payment payment) {

    }
}
