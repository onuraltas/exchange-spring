package com.exchange.spring.service.impl;

import com.exchange.spring.dto.AccountDto;
import com.exchange.spring.dto.message.TransactionMessageDto;
import com.exchange.spring.dto.request.DepositRequestDto;
import com.exchange.spring.dto.request.WithdrawRequestDto;
import com.exchange.spring.dto.response.RetrieveBalanceResponseDto;
import com.exchange.spring.entity.Account;
import com.exchange.spring.enums.ExceptionMessage;
import com.exchange.spring.enums.TransactionType;
import com.exchange.spring.exception.CustomizedException;
import com.exchange.spring.mapper.AccountMapper;
import com.exchange.spring.model.CommonServerEvent;
import com.exchange.spring.repository.jpa.AccountRepository;
import com.exchange.spring.service.AccountService;
import com.exchange.spring.service.ExchangeRateService;
import com.exchange.spring.util.CurrencyUtil;
import com.exchange.spring.util.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final WebUtils webUtils;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final StreamBridge streamBridge;
    private final Sinks.Many<CommonServerEvent> commonServerEventEmitSink;
    private final ExchangeRateService exchangeRateService;

    @Override
    public Mono<List<AccountDto>> getAccountList() {
        return webUtils.getCurrentUser()
                .map(currentUser -> accountRepository.findByUserId(currentUser.getUserId()).stream().map(accountMapper::toDto).toList());
    }

    @Override
    public Mono<RetrieveBalanceResponseDto> retrieveBalance(String targetCurrencyCode) {
        if (!CurrencyUtil.isValidCurrency(targetCurrencyCode)) {
            throw new CustomizedException(ExceptionMessage.UNKNOWN_CURRENCY, targetCurrencyCode);
        }

        var monoExchangeRateMap = exchangeRateService.getExchangeRate(targetCurrencyCode);

        return webUtils.getCurrentUser().zipWith(monoExchangeRateMap, (currentUser, exchangeRateMap) -> {

            var accountList = accountRepository.findByUserId(currentUser.getUserId());

            var totalBalance = accountList.stream()
                    .map(account -> account.getCurrencyCode().equals(targetCurrencyCode)
                            ? account.getBalance()
                            : account.getBalance().divide(exchangeRateMap.get(account.getCurrencyCode()), 2, RoundingMode.HALF_UP))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new RetrieveBalanceResponseDto(targetCurrencyCode, totalBalance);
        });
    }

    @Override
    public Mono<Boolean> startDeposit(DepositRequestDto requestDto) {
        return webUtils.getCurrentUser().map(currentUser -> {
            this.startDeposit(currentUser.getUserId(), requestDto.getCurrencyCode(), requestDto.getAmount());

            return true;
        });
    }

    private void startDeposit(Long userId, String currencyCode, BigDecimal amount) {
        if (!CurrencyUtil.isValidCurrency(currencyCode)) {
            throw new CustomizedException(ExceptionMessage.UNKNOWN_CURRENCY, currencyCode);
        }

        var transactionMessage = new TransactionMessageDto(TransactionType.DEPOSIT, userId, currencyCode, amount);

        streamBridge.send("transactionMessage-out-0", transactionMessage);
    }

    private void finishDeposit(Long userId, String currencyCode, BigDecimal amount) {
        var account = accountRepository.findByUserIdAndCurrencyCode(userId, currencyCode).orElseGet(() -> {
            var newAccount = new Account();
            newAccount.setUserId(userId);
            newAccount.setCurrencyCode(currencyCode);
            newAccount.setBalance(BigDecimal.ZERO);
            return newAccount;
        });

        account.setBalance(account.getBalance().add(amount));

        var savedAccount = accountRepository.save(account);

        var commonServerEvent = new CommonServerEvent(userId, savedAccount, String.format("Successfully deposited %s %s", amount, currencyCode));

        commonServerEventEmitSink.tryEmitNext(commonServerEvent);
    }

    @Override
    public Mono<Boolean> startWithdraw(WithdrawRequestDto requestDto) {
        return webUtils.getCurrentUser().map(currentUser -> {
            this.startWithdraw(currentUser.getUserId(), requestDto.getCurrencyCode(), requestDto.getAmount());

            return true;
        });
    }

    private void startWithdraw(Long userId, String currencyCode, BigDecimal amount) {
        if (!CurrencyUtil.isValidCurrency(currencyCode)) {
            throw new CustomizedException(ExceptionMessage.UNKNOWN_CURRENCY, currencyCode);
        }

        var transactionMessage = new TransactionMessageDto(TransactionType.WITHDRAW, userId, currencyCode, amount);

        streamBridge.send("transactionMessage-out-0", transactionMessage);
    }

    private void finishWithdraw(Long userId, String currencyCode, BigDecimal amount) {
        var account = accountRepository.findByUserIdAndCurrencyCode(userId, currencyCode).orElseGet(() -> {
            var newAccount = new Account();
            newAccount.setUserId(userId);
            newAccount.setCurrencyCode(currencyCode);
            newAccount.setBalance(BigDecimal.ZERO);
            return newAccount;
        });

        if (account.getBalance().compareTo(amount) < 0) {
            var commonServerEvent = new CommonServerEvent(userId, account, String.format("Withdraw failed: %s", ExceptionMessage.NOT_ENOUGH_BALANCE.getErrorMessage()));
            commonServerEventEmitSink.tryEmitNext(commonServerEvent);

            throw new CustomizedException(ExceptionMessage.NOT_ENOUGH_BALANCE);
        }

        account.setBalance(account.getBalance().subtract(amount));

        var savedAccount = accountRepository.save(account);

        var commonServerEvent = new CommonServerEvent(userId, savedAccount, String.format("Successfully withdrawn %s %s", amount, currencyCode));

        commonServerEventEmitSink.tryEmitNext(commonServerEvent);
    }

    @Override
    public void finishTransaction(TransactionMessageDto messageDto) {

        switch (messageDto.getTransactionType()) {
            case TransactionType.DEPOSIT -> finishDeposit(messageDto.getUserId(), messageDto.getCurrencyCode(), messageDto.getAmount());
            case TransactionType.WITHDRAW -> finishWithdraw(messageDto.getUserId(), messageDto.getCurrencyCode(), messageDto.getAmount());
            default -> log.error("Unknown transaction type: {}", messageDto.getTransactionType());
        }

    }


}
