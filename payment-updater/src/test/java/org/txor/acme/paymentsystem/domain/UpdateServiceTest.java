package org.txor.acme.paymentsystem.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UpdateServiceTest {

    @Test
    public void updateShouldPersistPaymentAndUpdateAccountLastPaymentDate() {
        Long accountId = 1234L;
        Payment payment = new Payment(4312L, accountId, "online", "4111111111111111", 195L);
        AccountRepository accountRepository = mock(AccountRepository.class);
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        UpdateService updateService = new UpdateService(paymentRepository, accountRepository);

        updateService.update(payment);

        verify(accountRepository).loadAccount(accountId);
        verify(paymentRepository).savePayment(payment);
        verify(accountRepository).updateLastPaymentDate(accountId, any(Instant.class));
    }

}
