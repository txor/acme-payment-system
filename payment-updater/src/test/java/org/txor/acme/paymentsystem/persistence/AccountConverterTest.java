package org.txor.acme.paymentsystem.persistence;

import org.junit.jupiter.api.Test;
import org.txor.acme.paymentsystem.domain.Account;

import static org.assertj.core.api.Assertions.assertThat;

class AccountConverterTest {

    @Test
    public void convertShouldCorrectlyConvertObjects() {
        Long accountId = 4123L;
        AccountEntity accountEntity = new AccountEntity(accountId);
        AccountConverter converter = new AccountConverter();

        Account convertedEntity = converter.convert(accountEntity);

        assertThat(convertedEntity.getAccountId()).isEqualTo(accountId);
    }

}
