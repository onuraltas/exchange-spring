package com.exchange.spring.service;

import com.exchange.spring.model.dto.AccountDto;
import com.exchange.spring.model.dto.message.TransactionMessageDto;
import com.exchange.spring.model.dto.request.DepositRequestDto;
import com.exchange.spring.model.dto.request.WithdrawRequestDto;
import com.exchange.spring.model.dto.response.RetrieveBalanceResponseDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountService {

    Mono<List<AccountDto>> getAccountList();

    Mono<RetrieveBalanceResponseDto> retrieveBalance(String targetCurrencyCode);

    Mono<Boolean> startDeposit(DepositRequestDto requestDto);

    Mono<Boolean> startWithdraw(WithdrawRequestDto requestDto);

    void finishTransaction(TransactionMessageDto messageDto);

}
