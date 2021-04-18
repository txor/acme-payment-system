package org.txor.acme.paymentsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest()
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class PaymentDispatcherApplicationFeatureTests {

    @Value("${payment-dispatcher.topic}")
    private String topic;

    @Value("${payment-dispatcher.update-url}")
    private String updateHost;

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
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(new RestTemplate());
        mockServer.expect(requestTo(updateHost))
                .andExpect(method(POST))
                .andExpect(content().json(jsonRequest))
                .andRespond(withSuccess());

        kafkaTemplate.send(topic, eventMessage);

        mockServer.verify(Duration.ofSeconds(10));
    }

}
