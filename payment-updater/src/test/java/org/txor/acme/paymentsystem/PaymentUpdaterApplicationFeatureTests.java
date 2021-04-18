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
import org.txor.acme.paymentsystem.testutils.PaymentTestRepository;

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
    private PaymentTestRepository paymentTestRepository;

    @Test
    void storePaymentDataAndUpdateAccountLastPaymentDateOnDatabase() {
        Long paymentId = 1234L;
        Long accountId = 4321L;
        String paymentType = "online";
        String creditCard = "4242424242424242";
        String amount = "543";
        Instant updateTime = Instant.now();
        String jsonRequest = "{\n" +
                "  \"paymentId\": \"" + paymentId + "\",\n" +
                "  \"accountId\": \"" + accountId + "\",\n" +
                "  \"paymentType\": \"" + paymentType + "\",\n" +
                "  \"creditCard\": \"" + creditCard + "\",\n" +
                "  \"amount\": \"" + amount + "\"\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);
        ResponseEntity<String> result = this.restTemplate
                .postForEntity("http://localhost:" + port + "/update", requestEntity, String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Optional<PaymentEntity> persistedPayment = this.paymentTestRepository.findById(paymentId);
        assertThat(persistedPayment).isPresent();
        assertThat(persistedPayment).map(PaymentEntity::getPaymentId).hasValue(paymentId);
        assertThat(persistedPayment).map(PaymentEntity::getPaymentType).hasValue(paymentType);
        assertThat(persistedPayment).map(PaymentEntity::getCreditCard).hasValue(creditCard);
        assertThat(persistedPayment).map(PaymentEntity::getAmount).hasValue(amount);
        assertThat(persistedPayment).map(payment -> payment.getAccount().getAccountId()).hasValue(accountId);
        assertThat(persistedPayment).map(PaymentEntity::getAccount).hasValueSatisfying(
                account -> {
                    assertThat(account.getAccountId()).isEqualTo(accountId);
                    assertThat(account.getLastPaymentDate()).isAfter(updateTime);
                }
        );
    }

}
