package com.exchange.spring.service.impl;

import com.exchange.spring.model.dto.request.ExchangeRequestDto;
import com.exchange.spring.model.dto.response.ExchangeResponseDto;
import com.exchange.spring.model.entity.Account;
import com.exchange.spring.model.entity.Exchange;
import com.exchange.spring.enums.ExceptionMessage;
import com.exchange.spring.exception.CustomizedException;
import com.exchange.spring.model.mapper.ExchangeMapper;
import com.exchange.spring.model.CommonServerEvent;
import com.exchange.spring.repository.jpa.AccountRepository;
import com.exchange.spring.repository.jpa.ExchangeRepository;
import com.exchange.spring.service.ExchangeRateService;
import com.exchange.spring.service.ExchangeService;
import com.exchange.spring.util.CurrencyUtil;
import com.exchange.spring.util.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Transactional
@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final WebUtils webUtils;
    private final ExchangeMapper exchangeMapper;
    private final ExchangeRepository exchangeRepository;
    private final AccountRepository accountRepository;
    private final ExchangeRateService exchangeRateService;
    private final Sinks.Many<CommonServerEvent> commonServerEventEmitSink;

    public Mono<ExchangeResponseDto> exchange(ExchangeRequestDto requestDto) {

        if (!CurrencyUtil.isValidCurrency(requestDto.getSourceCurrencyCode())) {
            throw new CustomizedException(ExceptionMessage.UNKNOWN_CURRENCY, requestDto.getSourceCurrencyCode());
        }

        if (!CurrencyUtil.isValidCurrency(requestDto.getTargetCurrencyCode())) {
            throw new CustomizedException(ExceptionMessage.UNKNOWN_CURRENCY, requestDto.getTargetCurrencyCode());
        }

        if (requestDto.getSourceCurrencyCode().equals(requestDto.getTargetCurrencyCode())) {
            throw new CustomizedException(ExceptionMessage.SAME_CURRENCY);
        }

        var monoExchangeRate = exchangeRateService.getExchangeRate(requestDto.getSourceCurrencyCode(), requestDto.getTargetCurrencyCode());

        return webUtils.getCurrentUser().zipWith(monoExchangeRate, (currentUser, exchangeRate) -> {
            var exchange = this.exchange(currentUser.getUserId(), requestDto.getSourceCurrencyCode().trim(), requestDto.getAmount(),
                    requestDto.getTargetCurrencyCode().trim(), exchangeRate);

            var createdExchange = exchangeRepository.save(exchange);

            var exchangeDto = exchangeMapper.toDto(createdExchange);

            return new ExchangeResponseDto(exchangeDto);
        });
    }

    public Exchange exchange(Long userId, String sourceCurrencyCode, BigDecimal sourceAmount, String targetCurrencyCode, BigDecimal exchangeRate) {

        var sourceAccount = accountRepository.findByUserIdAndCurrencyCode(userId, sourceCurrencyCode).orElseGet(() -> {
            var newAccount = new Account();
            newAccount.setUserId(userId);
            newAccount.setCurrencyCode(sourceCurrencyCode);
            newAccount.setBalance(BigDecimal.ZERO);
            return newAccount;
        });

        if (sourceAccount.getBalance().compareTo(sourceAmount) < 0) {
            throw new CustomizedException(ExceptionMessage.NOT_ENOUGH_BALANCE);
        }

        var targetAccount = accountRepository.findByUserIdAndCurrencyCode(userId, targetCurrencyCode).orElseGet(() -> {
            var newAccount = new Account();
            newAccount.setUserId(userId);
            newAccount.setCurrencyCode(targetCurrencyCode);
            newAccount.setBalance(BigDecimal.ZERO);
            return newAccount;
        });

        var targetAmount = sourceAmount.multiply(exchangeRate);

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(sourceAmount));

        targetAccount.setBalance(targetAccount.getBalance().add(targetAmount));

        var exchange = new Exchange();
        exchange.setUserId(userId);
        exchange.setSourceAmount(sourceAmount);
        exchange.setSourceCurrencyCode(sourceCurrencyCode);
        exchange.setTargetAmount(targetAmount);
        exchange.setTargetCurrencyCode(targetCurrencyCode);

        var savedSourceAccount = accountRepository.save(sourceAccount);

        var sourceCommonServerEvent = new CommonServerEvent(userId, savedSourceAccount,
                String.format("Successfully withdrawn %s %s", sourceAmount, sourceCurrencyCode));

        commonServerEventEmitSink.tryEmitNext(sourceCommonServerEvent);

        var savedTargetAccount = accountRepository.save(targetAccount);

        var targetCommonServerEvent = new CommonServerEvent(userId, savedTargetAccount,
                String.format("Successfully deposited %s %s", targetAmount, targetCurrencyCode));

        commonServerEventEmitSink.tryEmitNext(targetCommonServerEvent);

        return exchange;
    }

}
