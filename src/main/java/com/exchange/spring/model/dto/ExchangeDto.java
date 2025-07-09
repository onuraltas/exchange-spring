package com.exchange.spring.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeDto {

    private Long exchangeId;
    private String sourceCurrencyCode;
    private BigDecimal sourceAmount;
    private String targetCurrencyCode;
    private BigDecimal targetAmount;

}
