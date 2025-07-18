package com.exchange.spring.repository.jpa;

import com.exchange.spring.model.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
}
