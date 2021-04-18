package org.txor.acme.paymentsystem.domain;

import java.time.Instant;
import java.util.Optional;

public class UpdateService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    public UpdateService(PaymentRepository paymentRepository, AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    public void update(Payment payment) {
        Optional<Account> account = accountRepository.loadAccount(payment.getAccountId());
        paymentRepository.savePayment(payment);
        accountRepository.updateLastPaymentDate(account.get().getAccountId(), Instant.now());
    }
}
