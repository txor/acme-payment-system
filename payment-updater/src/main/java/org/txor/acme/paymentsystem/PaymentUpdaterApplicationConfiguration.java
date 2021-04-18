package org.txor.acme.paymentsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txor.acme.paymentsystem.domain.AccountRepository;
import org.txor.acme.paymentsystem.domain.PaymentRepository;
import org.txor.acme.paymentsystem.domain.UpdateService;

@Configuration
public class PaymentUpdaterApplicationConfiguration {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Bean
    public UpdateService updateService() {
        return new UpdateService(paymentRepository, accountRepository);
    }
}
