package org.txor.acme.paymentsystem.domain;

public class InvalidPaymentException extends Exception {

    public InvalidPaymentException(Exception e) {
        super(e);
    }
}
