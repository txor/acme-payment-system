package org.txor.acme.paymentsystem.domain;

public interface ApiClient {

    UpdateStatus sendUpdateData(Payment message) throws InvalidPaymentException;
}
