package org.txor.acme.paymentsystem.repository;

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
    private Instant lastPaymentDate;

    protected AccountEntity() {
    }

    public AccountEntity(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Instant getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(Instant paymentDate) {
        this.lastPaymentDate = paymentDate;
    }
}
