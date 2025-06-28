package com.exchange.spring.dto.response;

import com.exchange.spring.dto.ExchangeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeResponseDto {

    private ExchangeDto exchange;

}
