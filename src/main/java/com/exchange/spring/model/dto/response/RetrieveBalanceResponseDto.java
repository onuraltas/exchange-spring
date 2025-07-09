package com.exchange.spring.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveBalanceResponseDto {

    private String currencyCode;
    private BigDecimal totalBalance;

}
