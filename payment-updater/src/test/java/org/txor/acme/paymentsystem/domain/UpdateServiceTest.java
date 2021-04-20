package org.txor.acme.paymentsystem.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.txor.acme.paymentsystem.tools.TestMother.accountId;
import static org.txor.acme.paymentsystem.tools.TestMother.createPayment;

class UpdateServiceTest {

    @Test
    public void updateShouldPersistPaymentAndUpdateAccountLastPaymentDate() {
        Long accountId = 1234L;
        Payment payment = createPayment();
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.loadAccount(anyLong())).thenReturn(Optional.of(new Account(accountId)));
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        UpdateService updateService = new UpdateService(paymentRepository, accountRepository);

        updateService.update(payment);

        verify(paymentRepository).savePayment(payment);
        verify(accountRepository).updateLastPaymentDate(eq(accountId), any(Instant.class));
    }

    @Test
    public void updateShouldThrowExceptionIfTheRelatedAccountDoesNotExist() {
        Payment payment = createPayment();
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.loadAccount(anyLong())).thenReturn(Optional.empty());
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        UpdateService updateService = new UpdateService(paymentRepository, accountRepository);

        assertThrows(Exception.class, () -> {
            updateService.update(payment);

            verify(accountRepository).loadAccount(accountId);
            verifyNoInteractions(paymentRepository);
            verify(accountRepository, never()).updateLastPaymentDate(anyLong(), any(Instant.class));
        });
    }
}
