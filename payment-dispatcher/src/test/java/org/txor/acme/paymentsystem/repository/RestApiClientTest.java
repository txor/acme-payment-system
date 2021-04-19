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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(RestApiClient.class)
class RestApiClientTest {

    @Value("${payment-dispatcher.update.host}")
    private String host;

    @Value("${payment-dispatcher.update.port}")
    private String port;

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
        String jsonRequest = "{" +
                "\"payment_id\":\"" + paymentId + "\"," +
                "\"account_id\":\"" + accountId + "\"," +
                "\"payment_type\":\"" + paymentType + "\"," +
                "\"credit_card\":\"" + creditCard + "\"," +
                "\"amount\":\"" + amount + "\"" +
                "}";
        this.server.expect(requestTo("http://" + host + ":" + port + "/update"))
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
