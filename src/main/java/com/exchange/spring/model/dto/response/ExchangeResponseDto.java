package com.exchange.spring.model.dto.response;

import com.exchange.spring.model.dto.ExchangeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeResponseDto {

    private ExchangeDto exchange;

}
