package org.txor.acme.paymentsystem.repository;

import org.junit.jupiter.api.Test;
import org.txor.acme.paymentsystem.domain.Payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.txor.acme.paymentsystem.tools.TestMother.accountId;
import static org.txor.acme.paymentsystem.tools.TestMother.amount;
import static org.txor.acme.paymentsystem.tools.TestMother.createPayment;
import static org.txor.acme.paymentsystem.tools.TestMother.creditCard;
import static org.txor.acme.paymentsystem.tools.TestMother.paymentId;
import static org.txor.acme.paymentsystem.tools.TestMother.paymentType;

class PaymentConverterTest {

    @Test
    public void convertShouldCorrectlyConvertObjects() {
        Payment payment = createPayment();
        PaymentConverter converter = new PaymentConverter();

        PaymentEntity convertedEntity = converter.convert(payment);

        assertThat(convertedEntity.getPaymentId()).isEqualTo(paymentId);
        assertThat(convertedEntity.getAccount().getAccountId()).isEqualTo(accountId);
        assertThat(convertedEntity.getPaymentType()).isEqualTo(paymentType);
        assertThat(convertedEntity.getCreditCard()).isEqualTo(creditCard);
        assertThat(convertedEntity.getAmount()).isEqualTo(amount);
    }
}
