package com.exchange.spring.service;

import com.exchange.spring.dto.AccountDto;
import com.exchange.spring.dto.message.DepositMessageDto;
import com.exchange.spring.dto.message.WithdrawMessageDto;
import com.exchange.spring.dto.request.DepositRequestDto;
import com.exchange.spring.dto.request.WithdrawRequestDto;
import com.exchange.spring.dto.response.RetrieveBalanceResponseDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountService {

    Mono<List<AccountDto>> getAccountList();

    Mono<RetrieveBalanceResponseDto> retrieveBalance(String targetCurrencyCode);

    Mono<Boolean> startDeposit(DepositRequestDto requestDto);

    void finishDeposit(DepositMessageDto messageDto);

    Mono<Boolean> startWithdraw(WithdrawRequestDto requestDto);

    void finishWithdraw(WithdrawMessageDto messageDto);

}
