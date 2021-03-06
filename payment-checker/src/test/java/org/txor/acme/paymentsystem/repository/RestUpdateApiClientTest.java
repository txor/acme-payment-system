package org.txor.acme.paymentsystem.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.txor.acme.paymentsystem.tools.TestMother.createJsonRequest;
import static org.txor.acme.paymentsystem.tools.TestMother.createPayment;


@RestClientTest(RestUpdateApiClient.class)
class RestUpdateApiClientTest {

    @Value("${payment-checker.update.host}")
    private String host;

    @Value("${payment-checker.update.port}")
    private String port;

    @Autowired
    private RestUpdateApiClient restApiClient;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void shouldSendThePaymentAsJson() {
        this.server.expect(requestTo("http://" + host + ":" + port + "/update"))
                .andExpect(method(POST))
                .andExpect(content().json(createJsonRequest()))
                .andRespond(withSuccess());

        restApiClient.updatePayment(createPayment());

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
