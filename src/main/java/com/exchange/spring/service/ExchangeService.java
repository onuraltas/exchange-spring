package com.exchange.spring.service;

import com.exchange.spring.dto.request.ExchangeRequestDto;
import com.exchange.spring.dto.response.ExchangeResponseDto;
import com.exchange.spring.entity.Exchange;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ExchangeService {

    Mono<ExchangeResponseDto> exchange(ExchangeRequestDto requestDto);

    Exchange exchange(Long userId, String sourceCurrencyCode, BigDecimal sourceAmount, String targetCurrencyCode, BigDecimal exchangeRate);

}
