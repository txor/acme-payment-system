package org.txor.acme.paymentsystem.domain;

public interface PaymentRepository {

    void savePayment(Payment payment);
}
