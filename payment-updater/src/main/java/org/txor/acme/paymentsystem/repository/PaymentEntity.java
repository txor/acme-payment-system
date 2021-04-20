package org.txor.acme.paymentsystem.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    private String paymentId;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity accountId;
    private String paymentType;
    private String creditCard;
    private Long amount;

    protected PaymentEntity() {
    }

    public PaymentEntity(String paymentId, AccountEntity accountId, String paymentType, String creditCard, Long amount) {

        this.paymentId = paymentId;
        this.accountId = accountId;
        this.paymentType = paymentType;
        this.creditCard = creditCard;
        this.amount = amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public AccountEntity getAccount() {
        return accountId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public Long getAmount() {
        return amount;
    }
}
