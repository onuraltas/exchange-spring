package com.exchange.spring.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositMessageDto {

    @NotNull
    @Positive
    private Long userId;

    @NotBlank
    private String currencyCode;

    @NotNull
    @Positive
    private BigDecimal amount;

}
