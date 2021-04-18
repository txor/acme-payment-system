package org.txor.acme.paymentsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txor.acme.paymentsystem.domain.UpdateService;

@Configuration
public class PaymentUpdaterApplicationConfiguration {

    @Bean
    public UpdateService updateService() {
        return new UpdateService();
    }
}
