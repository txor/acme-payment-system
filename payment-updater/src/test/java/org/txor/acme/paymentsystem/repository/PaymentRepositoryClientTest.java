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
import static org.txor.acme.paymentsystem.tools.TestMother.createPayment;
import static org.txor.acme.paymentsystem.tools.TestMother.createPaymentEntity;

@ExtendWith(MockitoExtension.class)
class PaymentRepositoryClientTest {

    @Mock
    PaymentDatabaseRepository paymentDatabaseRepository;

    @Mock
    private PaymentConverter paymentConverter;

    @Test
    public void saveShouldConvertAndPersistTheGivenPayment() {
        Payment payment = createPayment();
        PaymentEntity paymentEntity = createPaymentEntity();
        when(paymentConverter.convert(any(Payment.class))).thenReturn(paymentEntity);
        PaymentRepositoryClient paymentRepositoryClient = new PaymentRepositoryClient(paymentDatabaseRepository, paymentConverter);

        paymentRepositoryClient.savePayment(payment);

        verify(paymentConverter).convert(eq(payment));
        verify(paymentDatabaseRepository).save(eq(paymentEntity));
    }

}
