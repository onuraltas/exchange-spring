package com.exchange.spring.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRequestDto {

    @NotBlank(message = "Source currency code is required")
    @Size(min = 3, max = 3)
    private String sourceCurrencyCode;

    @NotBlank(message = "Target currency code is required")
    @Size(min = 3, max = 3)
    private String targetCurrencyCode;

    @NotNull(message = "Amount is required")
    @Positive(message = "The amount should be a positive number")
    private BigDecimal amount;

}
