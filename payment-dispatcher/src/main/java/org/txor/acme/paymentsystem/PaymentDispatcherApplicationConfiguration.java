package org.txor.acme.paymentsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.txor.acme.paymentsystem.domain.ApiClient;
import org.txor.acme.paymentsystem.domain.DispatchService;
import org.txor.acme.paymentsystem.domain.PaymentConverter;

@Configuration
public class PaymentDispatcherApplicationConfiguration {

    @Autowired
    private ApiClient apiClient;

    @Bean
    public DispatchService createDispatchService() {
        return new DispatchService(apiClient);
    }

    @Bean
    public PaymentConverter createPaymentConverter() {
        return new PaymentConverter(new ObjectMapper());
    }
}
