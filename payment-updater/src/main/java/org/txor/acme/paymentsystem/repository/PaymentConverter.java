package org.txor.acme.paymentsystem.repository;

import org.springframework.stereotype.Component;
import org.txor.acme.paymentsystem.domain.Payment;

@Component
public class PaymentConverter {

    public PaymentEntity convert(Payment payment) {
        return new PaymentEntity(payment.getPaymentId(), new AccountEntity(payment.getAccountId()), payment.getPaymentType(), payment.getCreditCard(), payment.getAmount());
    }
}
