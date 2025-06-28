package com.exchange.spring.repository.redis;

import com.exchange.spring.model.ExchangeRateTemp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhangeRateTempRepository extends CrudRepository<ExchangeRateTemp, String> {
}
