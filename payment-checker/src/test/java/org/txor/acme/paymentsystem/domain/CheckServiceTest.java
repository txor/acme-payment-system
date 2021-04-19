package org.txor.acme.paymentsystem.domain;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CheckServiceTest {

    @Test
    public void shouldCheckThaPaymentAndIfOkSendItToUpdating() {
        CheckApiClient checkApiClient = mock(CheckApiClient.class);
        UpdateApiClient updateApiClient = mock(UpdateApiClient.class);
        Payment payment = new Payment();
        CheckService checkService = new CheckService(checkApiClient, updateApiClient);

        checkService.check(payment);

        verify(checkApiClient).checkPayment(any(Payment.class));
        verify(updateApiClient).updatePayment(any(Payment.class));
    }
}
