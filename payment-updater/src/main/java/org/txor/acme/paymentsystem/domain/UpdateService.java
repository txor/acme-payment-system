package org.txor.acme.paymentsystem.domain;

import java.time.Instant;

public class UpdateService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    public UpdateService(PaymentRepository paymentRepository, AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    public void update(Payment payment) {
        Account account = accountRepository.loadAccount(payment.getAccountId());
        paymentRepository.savePayment(payment);
        accountRepository.updateLastPaymentDate(account.getAccountId(), Instant.now());
    }
}
