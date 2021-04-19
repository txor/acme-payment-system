package org.txor.acme.paymentsystem;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentCheckerApplicationFeatureTests {

    @Value("${payment-checker.check.host}")
    private String checkHost;

    @Value("${payment-checker.check.port}")
    private Integer checkPort;

    @Value("${payment-checker.update.host}")
    private String updateHost;

    @Value("${payment-checker.update.port}")
    private Integer updatePort;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAPaymentUpdateThenCheckThatIsOkInThePaymentApiThenSendToUpdateApi() throws Exception {
        String paymentId = "1234";
        String accountId = "836";
        String paymentType = "online";
        String creditCard = "632456";
        String amount = "52";
        String paymentJsonRequest = createJsonRequest(paymentId, accountId, paymentType, creditCard, amount);
        WireMockServer paymentApiMockServer = new WireMockServer(checkPort);
        paymentApiMockServer.start();
        paymentApiMockServer.givenThat(WireMock.post(urlPathEqualTo("/check"))
                .withRequestBody(equalToJson(paymentJsonRequest))
                .willReturn(ok()));
        WireMockServer updateApiMockServer = new WireMockServer(updatePort);
        updateApiMockServer.start();
        updateApiMockServer.givenThat(WireMock.post(urlPathEqualTo("/update"))
                .withRequestBody(equalToJson(paymentJsonRequest))
                .willReturn(ok()));

        mockMvc.perform(post("/update")
                .contentType("application/json")
                .content(paymentJsonRequest))
                .andExpect(status().isOk());

        paymentApiMockServer.verify(exactly(1), postRequestedFor(urlPathEqualTo("/check")));
        paymentApiMockServer.stop();
        updateApiMockServer.verify(exactly(1), postRequestedFor(urlPathEqualTo("/update")));
        updateApiMockServer.stop();
    }

    private String createJsonRequest(String paymentId, String accountId, String paymentType, String creditCard, String amount) {
        return "{" +
                "\"payment_id\":\"" + paymentId + "\"," +
                "\"account_id\":\"" + accountId + "\"," +
                "\"payment_type\":\"" + paymentType + "\"," +
                "\"credit_card\":\"" + creditCard + "\"," +
                "\"amount\":\"" + amount + "\"" +
                "}";
    }

}

