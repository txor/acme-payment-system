package org.txor.acme.paymentsystem.domain;

public interface CheckApiClient {

    PaymentStatus checkPayment(Payment any);
}
