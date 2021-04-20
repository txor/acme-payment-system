package org.txor.acme.paymentsystem.tools;

import org.txor.acme.paymentsystem.domain.Payment;

public class TestMother {

    public static final String paymentId = "1234";
    public static final String accountId = "836";
    public static final String paymentType = "type";
    public static final String creditCard = "632456";
    public static final String amount = "52";
    public static final String delay = "435";

    public static String createJsonPayment() {
        return "{" +
                "\"payment_id\":\"" + paymentId + "\"," +
                "\"account_id\":\"" + accountId + "\"," +
                "\"payment_type\":\"" + paymentType + "\"," +
                "\"credit_card\":\"" + creditCard + "\"," +
                "\"amount\":\"" + amount + "\"" +
                "}";
    }

    public static String createEventMessage() {
        return "{" +
                "\"payment_id\": \"" + paymentId + "\", " +
                "\"account_id\": " + accountId + ", " +
                "\"payment_type\": \"" + paymentType + "\", " +
                "\"credit_card\": \"" + creditCard + "\", " +
                "\"amount\": " + amount + ", " +
                "\"delay\": " + delay + "}";
    }

    public static Payment createPayment() {
        return new Payment(paymentId, accountId, paymentType, creditCard, amount);
    }

}
