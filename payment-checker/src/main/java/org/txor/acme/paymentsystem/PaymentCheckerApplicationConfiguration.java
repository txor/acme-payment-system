package org.txor.acme.paymentsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txor.acme.paymentsystem.domain.CheckApiClient;
import org.txor.acme.paymentsystem.domain.CheckService;
import org.txor.acme.paymentsystem.domain.UpdateApiClient;

@Configuration
public class PaymentCheckerApplicationConfiguration {

    @Autowired
    CheckApiClient checkApiClient;

    @Autowired
    UpdateApiClient updateApiClient;

    @Bean
    public CheckService createCheckService() {
        return new CheckService(checkApiClient, updateApiClient);
    }
}
