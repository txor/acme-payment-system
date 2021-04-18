package org.txor.acme.paymentsystem.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    private Long accountId;
    private String name;
    private String email;
    private Instant birthdate;

    protected AccountEntity() {
    }

    public AccountEntity(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Instant getLastPaymentDate() {
        return null;
    }
}
