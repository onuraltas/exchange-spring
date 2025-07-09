package com.exchange.spring.controller;

import com.exchange.spring.model.dto.AccountDto;
import com.exchange.spring.model.dto.request.DepositRequestDto;
import com.exchange.spring.model.dto.request.WithdrawRequestDto;
import com.exchange.spring.model.dto.response.RetrieveBalanceResponseDto;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping("/rest/account")
public interface AccountController {

    @GetMapping
    Mono<ResponseEntity<List<AccountDto>>> getAccounts();

    @GetMapping(path = "/balance/{targetCurrencyCode}")
    Mono<ResponseEntity<RetrieveBalanceResponseDto>> retrieveBalance(@PathVariable @NotEmpty(message = "Missing required field") String targetCurrencyCode);

    @PostMapping("/deposit")
    Mono<ResponseEntity<Void>> deposit(@Validated @RequestBody DepositRequestDto requestDto);

    @PostMapping("/withdraw")
    Mono<ResponseEntity<Void>> withdraw(@Validated @RequestBody WithdrawRequestDto requestDto);

}
