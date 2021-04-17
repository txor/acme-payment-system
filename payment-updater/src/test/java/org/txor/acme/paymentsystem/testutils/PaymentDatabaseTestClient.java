package org.txor.acme.paymentsystem.testutils;

import org.springframework.data.repository.CrudRepository;
import org.txor.acme.paymentsystem.persistence.PaymentEntity;

public interface PaymentDatabaseTestClient extends CrudRepository<PaymentEntity, String> {
}
