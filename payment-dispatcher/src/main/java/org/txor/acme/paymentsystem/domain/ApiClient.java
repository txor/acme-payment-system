package org.txor.acme.paymentsystem.domain;

public interface ApiClient {

    void sendUpdateData(Payment message) throws InvalidPaymentException;
}
