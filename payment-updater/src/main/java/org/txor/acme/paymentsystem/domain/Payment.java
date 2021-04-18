package org.txor.acme.paymentsystem.domain;

public class Payment {

    private final Long paymentId;
    private final Long accountId;
    private final String paymentType;
    private final String creditCard;
    private final Long amount;

    public Payment(Long paymentId, Long accountId, String paymentType, String creditCard, Long amount) {
        this.paymentId = paymentId;
        this.accountId = accountId;
        this.paymentType = paymentType;
        this.creditCard = creditCard;
        this.amount = amount;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Long getAccountId() {
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
