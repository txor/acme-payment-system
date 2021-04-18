package org.txor.acme.paymentsystem.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.acme.paymentsystem.domain.Payment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentRepositoryClientTest {

    @Mock
    PaymentDatabaseRepository paymentDatabaseRepository;

    @Mock
    private PaymentConverter paymentConverter;

    @Test
    public void saveShouldConvertAndPersistTheGivenPayment() {
        Payment payment = new Payment(1234L, 4321L, "offline", "4242424242424242", 60L);
        PaymentEntity paymentEntity = new PaymentEntity(1234L, new AccountEntity(4321L), "offline", "4242424242424242", 60L);
        when(paymentConverter.convert(any(Payment.class))).thenReturn(paymentEntity);
        PaymentRepositoryClient paymentRepositoryClient = new PaymentRepositoryClient(paymentDatabaseRepository, paymentConverter);

        paymentRepositoryClient.savePayment(payment);

        verify(paymentConverter).convert(eq(payment));
        verify(paymentDatabaseRepository).save(eq(paymentEntity));
    }

}
