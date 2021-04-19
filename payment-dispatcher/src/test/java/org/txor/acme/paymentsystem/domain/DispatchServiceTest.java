package org.txor.acme.paymentsystem.domain;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DispatchServiceTest {

    @Test
    public void dispatchShouldSendTheReceivedData() {
        String message = "";
        ApiClient apiClient = mock(ApiClient.class);
        DispatchService dispatchService = new DispatchService(apiClient);

        dispatchService.dispatch(message);

        verify(apiClient).sendUpdateData(eq(message));
    }

}
