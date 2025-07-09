package com.exchange.spring.model;

import com.exchange.spring.model.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonServerEvent {

    private Long userId;
    private Account account;
    private String message;

}
