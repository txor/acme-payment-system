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
import org.txor.acme.paymentsystem.domain.PaymentConverter;
import org.txor.acme.paymentsystem.domain.UpdateStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.txor.acme.paymentsystem.tools.TestMother.createJsonPayment;
import static org.txor.acme.paymentsystem.tools.TestMother.createPayment;

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
        this.server.expect(requestTo("http://" + host + ":" + port + "/update"))
                .andExpect(method(POST))
                .andExpect(content().json(createJsonPayment()))
                .andRespond(withSuccess());

        UpdateStatus updateStatus = restApiClient.sendUpdateData(createPayment());

        server.verify();
        assertThat(updateStatus).isEqualTo(UpdateStatus.Ok);
    }

    @Test
    public void shouldSendThePaymentAsJsonAndReturnKoOnUpdateErrors() throws InvalidPaymentException {
        this.server.expect(requestTo("http://" + host + ":" + port + "/update"))
                .andExpect(method(POST))
                .andExpect(content().json(createJsonPayment()))
                .andRespond(withServerError());

        UpdateStatus updateStatus = restApiClient.sendUpdateData(createPayment());

        server.verify();
        assertThat(updateStatus).isEqualTo(UpdateStatus.Ko);
    }

    @TestConfiguration
    static class RestApiTestConfiguration {
        @Bean
        public PaymentConverter createPaymentConverter() {
            return new PaymentConverter(new ObjectMapper());
        }
    }
}
