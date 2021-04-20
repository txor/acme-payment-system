package org.txor.acme.paymentsystem.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.txor.acme.paymentsystem.tools.TestMother.createPayment;

class DispatchServiceTest {

    @Test
    public void dispatchShouldSendTheReceivedPaymentAndReturnBackTheResult() throws InvalidPaymentException {
        ApiClient apiClient = mock(ApiClient.class);
        when(apiClient.sendUpdateData(any(Payment.class))).thenReturn(UpdateStatus.Ok);
        DispatchService dispatchService = new DispatchService(apiClient);

        UpdateStatus status = dispatchService.dispatch(createPayment());

        assertThat(status).isEqualTo(UpdateStatus.Ok);
    }

}
