package org.txor.acme.paymentsystem.domain;

import java.time.Instant;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> loadAccount(Long accountId);

    void updateLastPaymentDate(Long accountId, Instant any);
}
