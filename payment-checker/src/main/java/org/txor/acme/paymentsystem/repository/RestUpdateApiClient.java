package org.txor.acme.paymentsystem.repository;

import org.springframework.stereotype.Component;
import org.txor.acme.paymentsystem.domain.Payment;
import org.txor.acme.paymentsystem.domain.UpdateApiClient;

@Component
public class RestUpdateApiClient implements UpdateApiClient {

    @Override
    public void updatePayment(Payment any) {

    }
}
