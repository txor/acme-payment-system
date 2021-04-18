package org.txor.acme.paymentsystem.repository;

import org.springframework.data.repository.CrudRepository;

public interface AccountDatabaseRepository extends CrudRepository<AccountEntity, Long> {
}
