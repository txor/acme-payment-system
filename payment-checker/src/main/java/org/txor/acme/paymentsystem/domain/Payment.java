package org.txor.acme.paymentsystem.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotNull;

@JsonPropertyOrder({"payment_id", "account_id", "payment_type", "credit_card", "amount"})
public class Payment {

    @NotNull
    @JsonProperty("payment_id")
    private String paymentId;
    @NotNull
    @JsonProperty("account_id")
    private String accountId;
    @NotNull
    @JsonProperty("payment_type")
    private String paymentType;
    @NotNull
    @JsonProperty("credit_card")
    private String creditCard;
    @NotNull
    private String amount;

    public Payment() {
    }

    public Payment(String paymentId, String accountId, String paymentType, String creditCard, String amount) {
        this.paymentId = paymentId;
        this.accountId = accountId;
        this.paymentType = paymentType;
        this.creditCard = creditCard;
        this.amount = amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getAccountId() {
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
