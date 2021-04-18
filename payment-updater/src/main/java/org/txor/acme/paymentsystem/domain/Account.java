package org.txor.acme.paymentsystem.domain;

public class Account {

    private final Long accountId;

    public Account(Long accountId) {

        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
