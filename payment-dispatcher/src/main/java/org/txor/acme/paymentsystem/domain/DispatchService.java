package org.txor.acme.paymentsystem.domain;

public class DispatchService {

    private final ApiClient apiClient;

    public DispatchService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void dispatch(String message) {
        apiClient.sendUpdateData(message);
    }

}
