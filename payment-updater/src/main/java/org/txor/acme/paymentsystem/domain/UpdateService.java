package org.txor.acme.paymentsystem.domain;

import org.txor.acme.paymentsystem.domain.exceptions.AccountNotFoundException;

import javax.transaction.Transactional;
import java.time.Instant;

public class UpdateService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    public UpdateService(PaymentRepository paymentRepository, AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void update(Payment payment) {
        accountRepository.loadAccount(payment.getAccountId()).map(
                account -> {
                    paymentRepository.savePayment(payment);
                    accountRepository.updateLastPaymentDate(account.getAccountId(), Instant.now());
                    return account;
                }
        ).orElseThrow(() -> new AccountNotFoundException(payment.getAccountId()));
    }

}
