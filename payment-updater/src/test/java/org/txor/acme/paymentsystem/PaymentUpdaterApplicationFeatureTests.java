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
import org.txor.acme.paymentsystem.repository.PaymentEntity;
import org.txor.acme.paymentsystem.testutils.PaymentTestRepository;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.txor.acme.paymentsystem.tools.TestMother.accountId;
import static org.txor.acme.paymentsystem.tools.TestMother.amount;
import static org.txor.acme.paymentsystem.tools.TestMother.createJsonRequest;
import static org.txor.acme.paymentsystem.tools.TestMother.createJsonRequestWithAccountId;
import static org.txor.acme.paymentsystem.tools.TestMother.creditCard;
import static org.txor.acme.paymentsystem.tools.TestMother.paymentId;
import static org.txor.acme.paymentsystem.tools.TestMother.paymentType;

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
        Instant updateTime = Instant.now();

        ResponseEntity<String> result = post(createJsonRequest());

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

    @Test
    void doNotUpdateDataWhenTheAccountDoesNotExist() {
        ResponseEntity<String> result = post(createJsonRequestWithAccountId(543));

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Optional<PaymentEntity> persistedPayment = this.paymentTestRepository.findById(paymentId);
        assertThat(persistedPayment).isEmpty();
    }

    @Test
    public void doNothingForABadRequest() {
        ResponseEntity<String> result = post("{\"not valid\":\"json\"}");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> post(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(message, headers);
        return this.restTemplate
                .postForEntity("http://localhost:" + port + "/update", requestEntity, String.class);
    }
}
