package org.txor.acme.paymentsystem.domain.exceptions;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long account) {
        super(account + " does not exist");
    }
}
