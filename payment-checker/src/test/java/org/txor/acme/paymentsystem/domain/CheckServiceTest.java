package org.txor.acme.paymentsystem.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckServiceTest {

    @Mock
    private CheckApiClient checkApiClient;

    @Mock
    private UpdateApiClient updateApiClient;

    @Test
    public void shouldCheckThaPaymentAndIfOkSendItToUpdating() throws InvalidPaymentException {
        Payment payment = new Payment();
        CheckService checkService = new CheckService(checkApiClient, updateApiClient);

        checkService.check(payment);

        verify(checkApiClient).checkPayment(any(Payment.class));
        verify(updateApiClient).updatePayment(any(Payment.class));
    }

    @Test
    public void shouldNotSendThePaymentToUpdatingOnPaymentCheckFail() throws InvalidPaymentException {
        Payment payment = new Payment();
        when(checkApiClient.checkPayment(any(Payment.class))).thenReturn(PaymentStatus.KO);
        CheckService checkService = new CheckService(checkApiClient, updateApiClient);

        checkService.check(payment);

        verify(checkApiClient).checkPayment(any(Payment.class));
        verifyNoInteractions(updateApiClient);
    }
}
