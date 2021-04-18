package org.txor.acme.paymentsystem.domain;

public class UpdateService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    public UpdateService(PaymentRepository paymentRepository, AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    public void update(Payment payment) {
    }
}
