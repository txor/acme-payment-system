package org.txor.acme.paymentsystem.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.txor.acme.paymentsystem.tools.TestMother;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentConverterTest {

    @Test
    public void shouldCorrectlyConvertAPaymentInToJsonString() {
        PaymentConverter paymentConverter = new PaymentConverter(new ObjectMapper());

        String convertedJson = paymentConverter.convert(TestMother.createPayment());

        assertThat(convertedJson).isEqualTo(TestMother.createJsonRequest());
    }

}
