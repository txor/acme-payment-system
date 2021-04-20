package org.txor.acme.paymentsystem.domain;

public class CheckService {

    private final CheckApiClient checkApiClient;
    private final UpdateApiClient updateApiClient;

    public CheckService(CheckApiClient checkApiClient, UpdateApiClient updateApiClient) {
        this.checkApiClient = checkApiClient;
        this.updateApiClient = updateApiClient;
    }

    public void check(Payment payment) {
        checkApiClient.checkPayment(payment);
        updateApiClient.updatePayment(payment);
    }
}
