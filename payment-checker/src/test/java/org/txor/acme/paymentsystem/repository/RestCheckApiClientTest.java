package org.txor.acme.paymentsystem.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.txor.acme.paymentsystem.domain.Payment;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(RestCheckApiClient.class)
class RestCheckApiClientTest {

    @Value("${payment-checker.check.host}")
    private String host;

    @Value("${payment-checker.check.port}")
    private String port;

    @Autowired
    private RestCheckApiClient restApiClient;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void shouldSendThePaymentAsJson() {
        this.server.expect(requestTo("http://" + host + ":" + port + "/check"))
                .andExpect(method(POST))
                .andExpect(content().json("{}"))
                .andRespond(withSuccess());

        restApiClient.checkPayment(new Payment());

        server.verify();
    }
}
