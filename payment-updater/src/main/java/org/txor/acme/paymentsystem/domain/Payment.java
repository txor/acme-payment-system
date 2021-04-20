package org.txor.acme.paymentsystem.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class Payment {

    @NotNull
    @JsonProperty("payment_id")
    private final Long paymentId;
    @NotNull
    @JsonProperty("account_id")
    private final Long accountId;
    @NotNull
    @JsonProperty("payment_type")
    private final String paymentType;
    @NotNull
    @JsonProperty("credit_card")
    private final String creditCard;
    @NotNull
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
