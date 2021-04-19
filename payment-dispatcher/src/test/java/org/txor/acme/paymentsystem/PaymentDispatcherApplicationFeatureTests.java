package org.txor.acme.paymentsystem;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class PaymentDispatcherApplicationFeatureTests {

    @Value("${payment-dispatcher.topic}")
    private String topic;

    @Value("${payment-dispatcher.update.port}")
    private Integer port;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void getMessageAndSendAnUpdateRequest() {
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
                "\"amount\": " + creditCard + ", " +
                "\"delay\": " + delay + "}";
        String jsonRequest = "{\n" +
                "  \"payment_id\": \"" + paymentId + "\",\n" +
                "  \"account_id\": \"" + accountId + "\",\n" +
                "  \"payment_type\": \"" + paymentType + "\",\n" +
                "  \"credit_card\": \"" + creditCard + "\",\n" +
                "  \"amount\": \"" + amount + "\"\n" +
                "}";
        WireMockServer wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        wireMockServer.givenThat(post(urlPathEqualTo("/update"))
                .willReturn(aResponse().withStatus(OK.value())));

        kafkaTemplate.send(topic, eventMessage);

        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/update")));
        wireMockServer.stop();
    }

}
