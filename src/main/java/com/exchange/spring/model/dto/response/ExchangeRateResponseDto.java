package com.exchange.spring.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateResponseDto {

    private String result;
    private String base_code;
    private Map<String, BigDecimal> conversion_rates;

}
