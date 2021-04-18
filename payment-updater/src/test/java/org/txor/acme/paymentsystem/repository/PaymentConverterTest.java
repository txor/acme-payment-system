package org.txor.acme.paymentsystem.repository;

import org.junit.jupiter.api.Test;
import org.txor.acme.paymentsystem.domain.Payment;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentConverterTest {

    @Test
    public void convertShouldCorrectlyConvertObjects() {
        long paymentId = 1234L;
        long accountId = 4123L;
        String paymentType = "offline";
        String creditCard = "4111111111111111";
        long amount = 45L;
        Payment payment = new Payment(paymentId, accountId, paymentType, creditCard, amount);
        PaymentConverter converter = new PaymentConverter();

        PaymentEntity convertedEntity = converter.convert(payment);

        assertThat(convertedEntity.getPaymentId()).isEqualTo(paymentId);
        assertThat(convertedEntity.getAccount().getAccountId()).isEqualTo(accountId);
        assertThat(convertedEntity.getPaymentType()).isEqualTo(paymentType);
        assertThat(convertedEntity.getCreditCard()).isEqualTo(creditCard);
        assertThat(convertedEntity.getAmount()).isEqualTo(amount);
    }
}
