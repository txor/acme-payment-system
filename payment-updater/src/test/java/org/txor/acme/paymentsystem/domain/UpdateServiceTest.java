package org.txor.acme.paymentsystem.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateServiceTest {

    @Test
    public void updateShouldPersistPaymentAndUpdateAccountLastPaymentDate() {
        Long accountId = 1234L;
        Payment payment = new Payment(4312L, accountId, "online", "4111111111111111", 195L);
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.loadAccount(anyLong())).thenReturn(new Account(accountId));
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        UpdateService updateService = new UpdateService(paymentRepository, accountRepository);

        updateService.update(payment);

        verify(accountRepository).loadAccount(accountId);
        verify(paymentRepository).savePayment(payment);
        verify(accountRepository).updateLastPaymentDate(eq(accountId), any(Instant.class));
    }
}
