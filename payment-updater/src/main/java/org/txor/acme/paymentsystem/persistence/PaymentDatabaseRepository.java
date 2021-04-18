package org.txor.acme.paymentsystem.persistence;

import org.springframework.data.repository.CrudRepository;

public interface PaymentDatabaseRepository extends CrudRepository<PaymentEntity, Long> {
}
