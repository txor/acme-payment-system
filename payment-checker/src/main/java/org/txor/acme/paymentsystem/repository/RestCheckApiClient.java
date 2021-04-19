package org.txor.acme.paymentsystem.repository;

import org.springframework.stereotype.Component;
import org.txor.acme.paymentsystem.domain.CheckApiClient;
import org.txor.acme.paymentsystem.domain.Payment;

@Component
public class RestCheckApiClient implements CheckApiClient {

    @Override
    public void checkPayment(Payment any) {

    }
}
