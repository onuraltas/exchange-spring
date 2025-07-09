package com.exchange.spring;

import com.exchange.spring.model.dto.request.ExchangeRequestDto;
import com.exchange.spring.model.entity.Account;
import com.exchange.spring.repository.jpa.AccountRepository;
import com.exchange.spring.repository.jpa.ExchangeRepository;
import com.exchange.spring.service.ExchangeRateService;
import com.exchange.spring.service.ExchangeService;
import com.exchange.spring.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
public class ExchangeServiceTest {

    @Autowired
    private ExchangeService exchangeService;

    @MockitoBean
    private WebUtils webUtils;

    @MockitoBean
    private AccountRepository accountRepository;

    @MockitoBean
    private ExchangeRepository exchangeRepository;

    @MockitoBean
    private ExchangeRateService exchangeRateService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void givenExchangeService_whenExchange_thenResultIsExchange() {
        var requestDto = new ExchangeRequestDto();
        requestDto.setSourceCurrencyCode("USD");
        requestDto.setTargetCurrencyCode("TRY");
        requestDto.setAmount(new BigDecimal(5));

        var sourceAccount = new Account();
        sourceAccount.setAccountId(1L);
        sourceAccount.setUserId(1L);
        sourceAccount.setCurrencyCode("USD");
        sourceAccount.setBalance(new BigDecimal(5));

        when(accountRepository.findByUserIdAndCurrencyCode(anyLong(), eq("USD"))).thenReturn(Optional.of(sourceAccount));

        var targetAccount = new Account();
        targetAccount.setAccountId(2L);
        targetAccount.setUserId(1L);
        targetAccount.setCurrencyCode("TRY");
        targetAccount.setBalance(new BigDecimal(0));

        when(accountRepository.findByUserIdAndCurrencyCode(anyLong(), eq("TRY"))).thenReturn(Optional.of(targetAccount));

        when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        var result = exchangeService.exchange(1L, "USD", new BigDecimal(3), "TRY", new BigDecimal("0.20"));

        assertEquals(new BigDecimal("0.60"), result.getTargetAmount());
    }


}
