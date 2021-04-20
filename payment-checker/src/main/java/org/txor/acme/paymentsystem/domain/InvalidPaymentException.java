package org.txor.acme.paymentsystem.domain;

public class InvalidPaymentException extends RuntimeException {

    public InvalidPaymentException(Exception e) {
        super(e);
    }

    public InvalidPaymentException(String reason) {
        super(reason);
    }
}
