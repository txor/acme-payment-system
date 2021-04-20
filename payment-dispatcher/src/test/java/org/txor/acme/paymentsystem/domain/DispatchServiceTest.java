package org.txor.acme.paymentsystem.domain;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.txor.acme.paymentsystem.tools.TestMother.createPayment;

class DispatchServiceTest {

    @Test
    public void dispatchShouldSendTheReceivedPayment() throws InvalidPaymentException {
        ApiClient apiClient = mock(ApiClient.class);
        DispatchService dispatchService = new DispatchService(apiClient);

        dispatchService.dispatch(createPayment());

        verify(apiClient).sendUpdateData(any(Payment.class));
    }

}
