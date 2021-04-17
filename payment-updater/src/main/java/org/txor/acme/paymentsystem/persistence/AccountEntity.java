package org.txor.acme.paymentsystem.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    private String accountId;

    public String getAccountId() {
        return null;
    }

    public Instant getLastPaymentDate() {
        return null;
    }
}
