package org.txor.acme.paymentsystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.txor.acme.paymentsystem.domain.Payment;
import org.txor.acme.paymentsystem.domain.PaymentRepository;

@Component
public class PaymentRepositoryClient implements PaymentRepository {

    private final PaymentDatabaseRepository paymentDatabaseRepository;
    private final PaymentConverter paymentConverter;

    @Autowired
    public PaymentRepositoryClient(PaymentDatabaseRepository paymentDatabaseRepository, PaymentConverter paymentConverter) {
        this.paymentDatabaseRepository = paymentDatabaseRepository;
        this.paymentConverter = paymentConverter;
    }

    @Override
    public void savePayment(Payment payment) {
        paymentDatabaseRepository.save(paymentConverter.convert(payment));
    }
}
