package org.txor.acme.paymentsystem.repository;

import org.springframework.stereotype.Component;
import org.txor.acme.paymentsystem.domain.Account;

@Component
public class AccountConverter {

    public Account convert(AccountEntity accountEntity) {
        return new Account(accountEntity.getAccountId());
    }
}
