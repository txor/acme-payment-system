package org.txor.acme.paymentsystem.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentConverterTest {

    @Test
    public void shouldConvertJsonStringToPayment() throws InvalidPaymentException {
        String paymentId = "1234";
        String accountId = "836";
        String paymentType = "type";
        String creditCard = "632456";
        String amount = "52";
        String jsonRequest = "{\n" +
                "  \"payment_id\": \"" + paymentId + "\",\n" +
                "  \"account_id\": \"" + accountId + "\",\n" +
                "  \"payment_type\": \"" + paymentType + "\",\n" +
                "  \"credit_card\": \"" + creditCard + "\",\n" +
                "  \"amount\": \"" + amount + "\"\n" +
                "}";
        PaymentConverter paymentConverter = new PaymentConverter(new ObjectMapper());

        Payment result = paymentConverter.convert(jsonRequest);

        assertThat(result.getPaymentId()).isEqualTo(paymentId);
        assertThat(result.getAccountId()).isEqualTo(accountId);
        assertThat(result.getPaymentType()).isEqualTo(paymentType);
        assertThat(result.getCreditCard()).isEqualTo(creditCard);
        assertThat(result.getAmount()).isEqualTo(amount);
    }

    @Test
    public void shouldConvertPaymentToJsonString() throws InvalidPaymentException {
        String paymentId = "1234";
        String accountId = "836";
        String paymentType = "type";
        String creditCard = "632456";
        String amount = "52";
        Payment payment = new Payment(paymentId, accountId, paymentType, creditCard, amount);
        String jsonRequest = "{" +
                "\"payment_id\":\"" + paymentId + "\"," +
                "\"account_id\":\"" + accountId + "\"," +
                "\"payment_type\":\"" + paymentType + "\"," +
                "\"credit_card\":\"" + creditCard + "\"," +
                "\"amount\":\"" + amount + "\"" +
                "}";
        PaymentConverter paymentConverter = new PaymentConverter(new ObjectMapper());

        String result = paymentConverter.convert(payment);

        assertThat(result).isEqualTo(jsonRequest);
    }

}
