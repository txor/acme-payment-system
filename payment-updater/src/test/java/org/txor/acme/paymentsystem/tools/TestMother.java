package org.txor.acme.paymentsystem.tools;

import org.txor.acme.paymentsystem.domain.Payment;
import org.txor.acme.paymentsystem.repository.AccountEntity;
import org.txor.acme.paymentsystem.repository.PaymentEntity;

public class TestMother {

    public static final String paymentId = "1234-23e34";
    public static final Long accountId = 1L;
    public static final String paymentType = "online";
    public static final String creditCard = "4242424242424242";
    public static final Long amount = 543L;

    public static String createJsonRequest() {
        return createJsonRequestWithAccountId(accountId);
    }

    public static String createJsonRequestWithAccountId(long accountId) {
        return "{" +
                "\"payment_id\":\"" + paymentId + "\"," +
                "\"account_id\":\"" + accountId + "\"," +
                "\"payment_type\":\"" + paymentType + "\"," +
                "\"credit_card\":\"" + creditCard + "\"," +
                "\"amount\":\"" + amount + "\"" +
                "}";
    }

    public static Payment createPayment() {
        return new Payment(paymentId, accountId, paymentType, creditCard, amount);
    }

    public static PaymentEntity createPaymentEntity() {
        return new PaymentEntity(paymentId, new AccountEntity(accountId), paymentType, creditCard, amount);
    }
}
