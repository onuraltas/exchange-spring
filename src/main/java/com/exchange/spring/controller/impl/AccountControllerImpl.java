package com.exchange.spring.controller.impl;

import com.exchange.spring.controller.AccountController;
import com.exchange.spring.dto.AccountDto;
import com.exchange.spring.dto.request.DepositRequestDto;
import com.exchange.spring.dto.request.WithdrawRequestDto;
import com.exchange.spring.dto.response.RetrieveBalanceResponseDto;
import com.exchange.spring.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    public Mono<ResponseEntity<List<AccountDto>>> getAccounts() {
        return accountService.getAccountList().map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<RetrieveBalanceResponseDto>> retrieveBalance(String targetCurrencyCode) {
        return accountService.retrieveBalance(targetCurrencyCode).map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deposit(DepositRequestDto requestDto) {
        return accountService.startDeposit(requestDto).map(result -> ResponseEntity.noContent().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> withdraw(WithdrawRequestDto requestDto) {
        return accountService.startWithdraw(requestDto).map(result -> ResponseEntity.noContent().build());
    }


}
