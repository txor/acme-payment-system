package org.txor.acme.paymentsystem;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.txor.acme.paymentsystem.tools.TestMother.createJsonRequest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    private WireMockServer paymentApiMockServer;
    private WireMockServer updateApiMockServer;

    @BeforeAll
    void setUp() {
        paymentApiMockServer = new WireMockServer(checkPort);
        paymentApiMockServer.start();
        updateApiMockServer = new WireMockServer(updatePort);
        updateApiMockServer.start();
    }

    @AfterAll
    void tearDown() {
        paymentApiMockServer.stop();
        updateApiMockServer.stop();
    }

    @Test
    public void getAPaymentUpdateThenCheckThatIsOkInThePaymentApiThenSendToUpdateApi() throws Exception {
        setupOkPaymentApi();
        setupOkUpdateApi();

        mockMvc.perform(post("/update")
                .contentType("application/json")
                .content(createJsonRequest()))
                .andExpect(status().isOk());

        verifyOneRequestToPaymentApi();
        verifyOneRequestToUpdateApi();
    }

    @Test
    public void returnNotFoundWhenCheckPaymentApiSaysThatThePaymentIsNotPayed() throws Exception {
        setupClientErrorPaymentApi();

        mockMvc.perform(post("/update")
                .contentType("application/json")
                .content(createJsonRequest()))
                .andExpect(status().isNotFound());

        verifyOneRequestToPaymentApi();
    }

    @Test
    public void doNothingForABadRequest() throws Exception {
        mockMvc.perform(post("/update")
                .contentType("application/json")
                .content("{\"not valid\":\"json\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void returnServiceUnavailableWhenCheckApiRespondedWithServerErrors() throws Exception {
        setupServerErrorPaymentApi();

        mockMvc.perform(post("/update")
                .contentType("application/json")
                .content(createJsonRequest()))
                .andExpect(status().isServiceUnavailable());

        verifyOneRequestToPaymentApi();
    }

    @Test
    public void returnServiceUnavailableWhenUpdateApiRespondedWithServerErrors() throws Exception {
        setupOkPaymentApi();
        setupServerErrorUpdateApi();

        mockMvc.perform(post("/update")
                .contentType("application/json")
                .content(createJsonRequest()))
                .andExpect(status().isServiceUnavailable());

        verifyOneRequestToPaymentApi();
        verifyOneRequestToUpdateApi();
    }

    private void setupOkPaymentApi() {
        paymentApiMockServer.givenThat(WireMock.post(urlPathEqualTo("/check"))
                .withRequestBody(equalToJson(createJsonRequest()))
                .willReturn(ok()));
    }

    private void setupClientErrorPaymentApi() {
        paymentApiMockServer.givenThat(WireMock.post(urlPathEqualTo("/check"))
                .withRequestBody(equalToJson(createJsonRequest()))
                .willReturn(notFound()));
    }

    private void setupServerErrorPaymentApi() {
        paymentApiMockServer.givenThat(WireMock.post(urlPathEqualTo("/check"))
                .withRequestBody(equalToJson(createJsonRequest()))
                .willReturn(serverError()));
    }

    private void setupOkUpdateApi() {
        updateApiMockServer.givenThat(WireMock.post(urlPathEqualTo("/update"))
                .withRequestBody(equalToJson(createJsonRequest()))
                .willReturn(ok()));
    }

    private void setupServerErrorUpdateApi() {
        updateApiMockServer.givenThat(WireMock.post(urlPathEqualTo("/update"))
                .withRequestBody(equalToJson(createJsonRequest()))
                .willReturn(serverError()));
    }

    private void verifyOneRequestToPaymentApi() {
        paymentApiMockServer.verify(exactly(1), postRequestedFor(urlPathEqualTo("/check")));
        paymentApiMockServer.resetAll();
    }

    private void verifyOneRequestToUpdateApi() {
        updateApiMockServer.verify(exactly(1), postRequestedFor(urlPathEqualTo("/update")));
        updateApiMockServer.resetAll();
    }

}
