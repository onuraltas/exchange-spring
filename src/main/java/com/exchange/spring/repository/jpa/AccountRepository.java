package com.exchange.spring.repository.jpa;

import com.exchange.spring.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserIdAndCurrencyCode(@Param("userId") Long userId, @Param("currencyCode") String currencyCode);

    List<Account> findByUserId(Long userId);

}
