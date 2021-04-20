package org.txor.acme.paymentsystem.domain;

public interface CheckApiClient {

    void checkPayment(Payment any) throws InvalidPaymentException;
}
