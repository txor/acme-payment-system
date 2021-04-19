package org.txor.acme.paymentsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"payment_id", "account_id", "payment_type", "credit_card", "amount"})
public class Payment {

    @JsonProperty("payment_id")
    private String paymentId;
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonProperty("credit_card")
    private String creditCard;
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
