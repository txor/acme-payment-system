package org.txor.acme.paymentsystem;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class PaymentDispatcherApplicationFeatureTests {

    @Value("${payment-dispatcher.topic}")
    private String topic;

    @Value("${payment-dispatcher.update.host}")
    private String host;

    @Value("${payment-dispatcher.update.port}")
    private Integer port;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void getMessageAndSendAnUpdateRequest() throws InterruptedException {
        String paymentId = "1234";
        String accountId = "836";
        String paymentType = "type";
        String creditCard = "632456";
        String amount = "52";
        String delay = "435";
        String eventMessage = "{" +
                "\"payment_id\": \"" + paymentId + "\", " +
                "\"account_id\": " + accountId + ", " +
                "\"payment_type\": \"" + paymentType + "\", " +
                "\"credit_card\": \"" + creditCard + "\", " +
                "\"amount\": " + amount + ", " +
                "\"delay\": " + delay + "}";
        String jsonRequest = "{" +
                "\"payment_id\":\"" + paymentId + "\"," +
                "\"account_id\":\"" + accountId + "\"," +
                "\"payment_type\":\"" + paymentType + "\"," +
                "\"credit_card\":\"" + creditCard + "\"," +
                "\"amount\":\"" + amount + "\"" +
                "}";
        WireMockServer wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        configureFor(host, port);
        wireMockServer.givenThat(post(urlPathEqualTo("/update"))
                .withRequestBody(equalToJson(jsonRequest))
                .willReturn(ok()));

        kafkaTemplate.send(topic, eventMessage);
        TimeUnit.SECONDS.sleep(3);

        wireMockServer.verify(exactly(1), postRequestedFor(urlPathEqualTo("/update")));
        wireMockServer.stop();
    }

}
