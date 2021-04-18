package org.txor.acme.paymentsystem.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.txor.acme.paymentsystem.domain.Account;
import org.txor.acme.paymentsystem.domain.AccountRepository;

import java.time.Instant;
import java.util.Optional;

@Component
public class AccountRepositoryClient implements AccountRepository {

    private final AccountDatabaseRepository accountDatabaseRepository;
    private final AccountConverter converter;

    @Autowired
    public AccountRepositoryClient(AccountDatabaseRepository accountDatabaseRepository, AccountConverter converter) {
        this.accountDatabaseRepository = accountDatabaseRepository;
        this.converter = converter;
    }

    @Override
    public Optional<Account> loadAccount(Long accountId) {
        return accountDatabaseRepository.findById(accountId).map(converter::convert);
    }

    @Override
    public void updateLastPaymentDate(Long accountId, Instant any) {

    }
}
