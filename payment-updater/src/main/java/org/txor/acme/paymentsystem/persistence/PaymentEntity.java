package org.txor.acme.paymentsystem.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    private String paymentId;

    public String getId() {
        return null;
    }

    public String getAccountId() {
        return null;
    }

    public String getPaymentType() {
        return null;
    }

    public String getCreditCard() {
        return null;
    }

    public String getAmount() {
        return null;
    }

    public AccountEntity getAccount() {
        return null;
    }
}
