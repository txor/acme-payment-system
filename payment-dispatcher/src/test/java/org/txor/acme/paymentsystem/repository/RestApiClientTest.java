package org.txor.acme.paymentsystem.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.txor.acme.paymentsystem.domain.InvalidPaymentException;
import org.txor.acme.paymentsystem.domain.Payment;
import org.txor.acme.paymentsystem.domain.PaymentConverter;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(RestApiClient.class)
class RestApiClientTest {

    @Value("${payment-dispatcher.update-url}")
    private String updateHost;

    @Autowired
    private RestApiClient restApiClient;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void shouldSendThePaymentAsJson() throws InvalidPaymentException {
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
        this.server.expect(requestTo(updateHost))
                .andExpect(method(POST))
                .andExpect(content().json(jsonRequest))
                .andRespond(withSuccess());

        restApiClient.sendUpdateData(new Payment(paymentId, accountId, paymentType, creditCard, amount));

        server.verify();
    }

    @TestConfiguration
    static class RestApiTestConfiguration {
        @Bean
        public PaymentConverter createPaymentConverter() {
            return new PaymentConverter(new ObjectMapper());
        }
    }
}
