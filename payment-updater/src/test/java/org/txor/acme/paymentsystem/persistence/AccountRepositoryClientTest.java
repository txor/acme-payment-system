package org.txor.acme.paymentsystem.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.txor.acme.paymentsystem.domain.Account;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryClientTest {

    @Mock
    private AccountDatabaseRepository accountDatabaseRepository;

    @Test
    public void loadAccountShouldLoadAndConvertTheGivenAccountFromDatabase() {
        Long accountId = 1234L;
        when(accountDatabaseRepository.findById(anyLong())).thenReturn(Optional.of(new AccountEntity(accountId)));
        AccountRepositoryClient accountRepositoryClient = new AccountRepositoryClient(accountDatabaseRepository, new AccountConverter());

        Optional<Account> account = accountRepositoryClient.loadAccount(accountId);

        assertThat(account).map(Account::getAccountId).hasValue(accountId);
    }

}
