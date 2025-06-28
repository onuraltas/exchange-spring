package com.exchange.spring.service;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;

public interface ExchangeRateService {

    Mono<Map<String, BigDecimal>> getExchangeRate(String sourceCurrencyCode);

    Mono<BigDecimal> getExchangeRate(String sourceCurrencyCode, String targetCurrencyCode);

}
