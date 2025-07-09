package com.exchange.spring.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private Long accountId;
    private String currencyCode;
    private BigDecimal balance;

}
