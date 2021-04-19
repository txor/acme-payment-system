package org.txor.acme.paymentsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txor.acme.paymentsystem.domain.CheckService;

@Configuration
public class PaymentCheckerApplicationConfiguration {

    @Bean
    public CheckService createCheckService() {
        return new CheckService();
    }
}
