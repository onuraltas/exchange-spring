package com.exchange.spring.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRequestDto {

    @NotBlank(message = "Currency code is required")
    @Size(min = 3, max = 3)
    private String currencyCode;

    @NotNull(message = "Amount is required")
    @Positive(message = "The amount should be a positive number")
    private BigDecimal amount;

}
