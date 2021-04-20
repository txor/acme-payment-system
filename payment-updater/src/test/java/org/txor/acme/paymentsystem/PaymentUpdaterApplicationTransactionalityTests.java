package org.txor.acme.paymentsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.txor.acme.paymentsystem.repository.AccountDatabaseRepository;
import org.txor.acme.paymentsystem.repository.AccountEntity;
import org.txor.acme.paymentsystem.repository.PaymentEntity;
import org.txor.acme.paymentsystem.testutils.PaymentTestRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.txor.acme.paymentsystem.tools.TestMother.accountId;
import static org.txor.acme.paymentsystem.tools.TestMother.createJsonRequest;
import static org.txor.acme.paymentsystem.tools.TestMother.paymentId;

@SpringBootTest(classes = {PaymentUpdaterApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentUpdaterApplicationTransactionalityTests {

    @LocalServerPort
    private int port;

    @MockBean
    private AccountDatabaseRepository accountDatabaseRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PaymentTestRepository paymentTestRepository;

    @Test
    void ifThereIsAnUnexpectedErrorUpdatingDataThereWillBeNoUpdatesInTheDatabase() {
        when(accountDatabaseRepository.findById(any())).thenReturn(Optional.of(new AccountEntity(accountId)));
        when(accountDatabaseRepository.save(any())).thenThrow(new RuntimeException());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(createJsonRequest(), headers);
        ResponseEntity<String> result = this.restTemplate
                .postForEntity("http://localhost:" + port + "/update", requestEntity, String.class);

        Optional<PaymentEntity> persistedPayment = this.paymentTestRepository.findById(paymentId);
        assertThat(persistedPayment).isEmpty();
    }

}
