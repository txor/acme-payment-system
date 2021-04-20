package org.txor.acme.paymentsystem.testutils;

import org.springframework.data.repository.CrudRepository;
import org.txor.acme.paymentsystem.repository.PaymentEntity;

public interface PaymentTestRepository extends CrudRepository<PaymentEntity, String> {
}
