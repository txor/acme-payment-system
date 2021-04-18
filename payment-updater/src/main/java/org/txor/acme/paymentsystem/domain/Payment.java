package org.txor.acme.paymentsystem.domain;

public class Payment {

    private final String paymentId;
    private final Long accountId;
    private final String paymentType;
    private final String creditCard;
    private final String amount;

    public Payment(String paymentId, Long accountId, String paymentType, String creditCard, String amount) {
        this.paymentId = paymentId;
        this.accountId = accountId;
        this.paymentType = paymentType;
        this.creditCard = creditCard;
        this.amount = amount;
    }

    public String getPaymentId() {
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

    public String getAmount() {
        return amount;
    }
}
