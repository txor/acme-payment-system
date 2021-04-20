package org.txor.acme.paymentsystem;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.txor.acme.paymentsystem.tools.TestMother.createEventMessage;
import static org.txor.acme.paymentsystem.tools.TestMother.createJsonPayment;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class PaymentDispatcherApplicationFeatureTests {

    @Value("${payment-dispatcher.topic}")
    private String topic;

    @Value("${payment-dispatcher.update.port}")
    private Integer port;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private WireMockServer paymentProcessingApi;

    @BeforeAll
    void setUp() {
        paymentProcessingApi = new WireMockServer(port);
        paymentProcessingApi.start();
    }

    @AfterAll
    void tearDown() {
        paymentProcessingApi.stop();
    }

    @Test
    public void getMessageAndSendAnUpdateRequest() throws InterruptedException {
        paymentProcessingApi.givenThat(post(urlPathEqualTo("/update"))
                .withRequestBody(equalToJson(createJsonPayment()))
                .willReturn(ok()));

        kafkaTemplate.send(topic, createEventMessage());
        TimeUnit.SECONDS.sleep(3);

        paymentProcessingApi.verify(exactly(1), postRequestedFor(urlPathEqualTo("/update")));
        paymentProcessingApi.resetAll();
    }

}
