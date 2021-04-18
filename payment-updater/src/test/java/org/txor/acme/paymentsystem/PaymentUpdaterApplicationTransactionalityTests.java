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
import org.txor.acme.paymentsystem.persistence.AccountDatabaseRepository;
import org.txor.acme.paymentsystem.persistence.AccountEntity;
import org.txor.acme.paymentsystem.persistence.PaymentEntity;
import org.txor.acme.paymentsystem.testutils.PaymentTestRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        long paymentId = 1234L;
        long accountId = 1L;
        String paymentType = "online";
        String creditCard = "4242424242424242";
        long amount = 543L;
        String jsonRequest = "{\n" +
                "  \"paymentId\": \"" + paymentId + "\",\n" +
                "  \"accountId\": \"" + accountId + "\",\n" +
                "  \"paymentType\": \"" + paymentType + "\",\n" +
                "  \"creditCard\": \"" + creditCard + "\",\n" +
                "  \"amount\": \"" + amount + "\"\n" +
                "}";
        when(accountDatabaseRepository.findById(any())).thenReturn(Optional.of(new AccountEntity(accountId)));
        when(accountDatabaseRepository.save(any())).thenThrow(new RuntimeException());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);
        ResponseEntity<String> result = this.restTemplate
                .postForEntity("http://localhost:" + port + "/update", requestEntity, String.class);

        Optional<PaymentEntity> persistedPayment = this.paymentTestRepository.findById(paymentId);
        assertThat(persistedPayment).isEmpty();
    }

}
