package org.txor.acme.paymentsystem.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.txor.acme.paymentsystem.domain.Account;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = {
        AccountRepositoryClient.class,
        AccountDatabaseRepository.class,
        AccountConverter.class})
@EnableAutoConfiguration
@EnableJpaRepositories
class AccountRepositoryClientTest {

    @Autowired
    private AccountRepositoryClient accountRepositoryClient;

    @Autowired
    private AccountDatabaseRepository accountDatabaseRepository;

    @Autowired
    private AccountConverter accountConverter;

    @Test
    public void loadAccountShouldLoadAndConvertTheGivenAccountFromDatabase() {
        Long accountId = 1L;

        Optional<Account> account = accountRepositoryClient.loadAccount(accountId);

        assertThat(account).map(Account::getAccountId).hasValue(accountId);
    }

    @Test
    public void updateLastPaymentDateShouldUpdateLastPaymentDateOfTheGivenAccount() {
        Long accountId = 1L;
        Instant now = Instant.now();

        accountRepositoryClient.updateLastPaymentDate(accountId, now);

        Optional<AccountEntity> updatedEntity = accountDatabaseRepository.findById(accountId);
        assertThat(updatedEntity).isPresent().map(AccountEntity::getLastPaymentDate).hasValue(now);
    }

}
