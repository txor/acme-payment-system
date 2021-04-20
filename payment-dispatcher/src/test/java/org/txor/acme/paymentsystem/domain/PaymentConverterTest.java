package org.txor.acme.paymentsystem.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.txor.acme.paymentsystem.tools.TestMother.accountId;
import static org.txor.acme.paymentsystem.tools.TestMother.amount;
import static org.txor.acme.paymentsystem.tools.TestMother.createJsonPayment;
import static org.txor.acme.paymentsystem.tools.TestMother.createPayment;
import static org.txor.acme.paymentsystem.tools.TestMother.creditCard;
import static org.txor.acme.paymentsystem.tools.TestMother.paymentId;
import static org.txor.acme.paymentsystem.tools.TestMother.paymentType;

class PaymentConverterTest {

    @Test
    public void shouldConvertJsonStringToPayment() throws InvalidPaymentException {
        PaymentConverter paymentConverter = new PaymentConverter(new ObjectMapper());

        Payment result = paymentConverter.convert(createJsonPayment());

        assertThat(result.getPaymentId()).isEqualTo(paymentId);
        assertThat(result.getAccountId()).isEqualTo(accountId);
        assertThat(result.getPaymentType()).isEqualTo(paymentType);
        assertThat(result.getCreditCard()).isEqualTo(creditCard);
        assertThat(result.getAmount()).isEqualTo(amount);
    }

    @Test
    public void shouldConvertPaymentToJsonString() throws InvalidPaymentException {
        PaymentConverter paymentConverter = new PaymentConverter(new ObjectMapper());

        String result = paymentConverter.convert(createPayment());

        assertThat(result).isEqualTo(createJsonPayment());
    }

}
