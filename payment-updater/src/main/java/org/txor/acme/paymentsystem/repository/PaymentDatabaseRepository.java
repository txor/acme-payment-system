package org.txor.acme.paymentsystem.repository;

import org.springframework.data.repository.CrudRepository;

public interface PaymentDatabaseRepository extends CrudRepository<PaymentEntity, String> {
}
