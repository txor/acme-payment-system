package org.txor.acme.paymentsystem.domain;

public class DispatchService {

    private final ApiClient apiClient;

    public DispatchService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public UpdateStatus dispatch(Payment message) throws InvalidPaymentException {
        return apiClient.sendUpdateData(message);
    }

}
