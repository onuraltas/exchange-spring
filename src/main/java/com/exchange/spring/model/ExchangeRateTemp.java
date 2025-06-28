package com.exchange.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "ExchangeRateTemp", timeToLive = 60L)
public class ExchangeRateTemp {

    @Id
    @Indexed
    private String baseCode;

    private Map<String, BigDecimal> conversionRates;


}
