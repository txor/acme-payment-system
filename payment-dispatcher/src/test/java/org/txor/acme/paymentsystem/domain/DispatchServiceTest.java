package org.txor.acme.paymentsystem.domain;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DispatchServiceTest {

    @Test
    public void dispatchShouldSendTheReceivedPayment() throws InvalidPaymentException {
        Payment payment = new Payment("1234", "836", "type", "632456", "52");
        ApiClient apiClient = mock(ApiClient.class);
        DispatchService dispatchService = new DispatchService(apiClient);

        dispatchService.dispatch(payment);

        verify(apiClient).sendUpdateData(any(Payment.class));
    }

}
