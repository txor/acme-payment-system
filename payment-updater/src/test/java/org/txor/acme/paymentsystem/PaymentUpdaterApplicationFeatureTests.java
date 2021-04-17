package org.txor.acme.paymentsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.txor.acme.paymentsystem.persistence.PaymentEntity;
import org.txor.acme.paymentsystem.testutils.PaymentDatabaseTestClient;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = PaymentUpdaterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentUpdaterApplicationFeatureTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PaymentDatabaseTestClient paymentDatabaseTestClient;

    @Test
    void storePaymentDataAndUpdateAccountLastPaymentDateOnDatabase() {
        String paymentId = "1234";
        String accountId = "4321";
        String paymentType = "online";
        String creditCard = "4242424242424242";
        String amount = "543";
        Instant updateTime = Instant.now();
        String jsonRequest = "{\n" +
                "  \"payment_id\": \"" + paymentId + "\",\n" +
                "  \"account_id\": \"" + accountId + "\",\n" +
                "  \"payment_type\": \"" + paymentType + "\",\n" +
                "  \"credit_card\": \"" + creditCard + "\",\n" +
                "  \"amount\": \"" + amount + "\"\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);
        ResponseEntity<String> result = this.restTemplate
                .postForEntity("http://localhost:" + port + "/update", requestEntity, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Optional<PaymentEntity> persistedPayment = this.paymentDatabaseTestClient.findById(paymentId);
        assertThat(persistedPayment).map(PaymentEntity::getId).hasValue(paymentId);
        assertThat(persistedPayment).map(PaymentEntity::getPaymentType).hasValue(paymentType);
        assertThat(persistedPayment).map(PaymentEntity::getCreditCard).hasValue(creditCard);
        assertThat(persistedPayment).map(PaymentEntity::getAmount).hasValue(amount);
        assertThat(persistedPayment).map(payment -> payment.getAccount().getId()).hasValue(accountId);
        assertThat(persistedPayment).map(PaymentEntity::getAccount).hasValueSatisfying(
                account -> {
                    assertThat(account.getId()).isEqualTo(accountId);
                    assertThat(account.getLastPaymentDate()).isAfter(updateTime);
                }
        );
    }

}
