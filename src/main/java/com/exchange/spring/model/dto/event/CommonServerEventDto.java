package com.exchange.spring.model.dto.event;

import com.exchange.spring.model.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonServerEventDto {

    private AccountDto account;
    private String message;

}
