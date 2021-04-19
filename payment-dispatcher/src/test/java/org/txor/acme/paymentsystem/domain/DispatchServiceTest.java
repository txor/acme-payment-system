package org.txor.acme.paymentsystem.domain;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DispatchServiceTest {

    @Test
    public void dispatchShouldSendTheReceivedPayment() {
        Payment payment = new Payment();
        ApiClient apiClient = mock(ApiClient.class);
        DispatchService dispatchService = new DispatchService(apiClient);

        dispatchService.dispatch(payment);

        verify(apiClient).sendUpdateData(any(Payment.class));
    }

}
