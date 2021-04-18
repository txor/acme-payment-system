package org.txor.acme.paymentsystem.persistence;

import org.springframework.data.repository.CrudRepository;

public interface AccountDatabaseRepository extends CrudRepository<AccountEntity, Long> {
}
