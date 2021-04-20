package org.txor.acme.paymentsystem.tools;

import org.txor.acme.paymentsystem.domain.Payment;

public class TestMother {

    private static final String PAYMENTID = "1234";
    private static final String ACCOUNTID = "836";
    private static final String PAYMENTTYPE = "online";
    private static final String CREDITCARD = "632456";
    private static final String AMOUNT = "52";

    public static String createJsonRequest() {
        return "{" +
                "\"payment_id\":\"" + PAYMENTID + "\"," +
                "\"account_id\":\"" + ACCOUNTID + "\"," +
                "\"payment_type\":\"" + PAYMENTTYPE + "\"," +
                "\"credit_card\":\"" + CREDITCARD + "\"," +
                "\"amount\":\"" + AMOUNT + "\"" +
                "}";
    }

    public static Payment createPayment() {
        return new Payment(PAYMENTID, ACCOUNTID, PAYMENTTYPE, CREDITCARD, AMOUNT);
    }

}
