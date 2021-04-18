package org.txor.acme.paymentsystem.domain;

import java.time.Instant;

public interface AccountRepository {

    Account loadAccount(Long accountId);

    void updateLastPaymentDate(Long accountId, Instant any);
}
