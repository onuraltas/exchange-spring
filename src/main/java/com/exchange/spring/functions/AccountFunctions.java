package com.exchange.spring.functions;

import com.exchange.spring.dto.message.DepositMessageDto;
import com.exchange.spring.dto.message.WithdrawMessageDto;
import com.exchange.spring.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class AccountFunctions {

    @Bean
    public Consumer<DepositMessageDto> depositMessage(AccountService accountService) {
        return depositMessageDto -> {
            log.info("depositMessageDto : " + depositMessageDto);

            accountService.finishDeposit(depositMessageDto);
        };
    }

    @Bean
    public Consumer<WithdrawMessageDto> withdrawMessage(AccountService accountService) {
        return withdrawMessageDto -> {
            log.info("withdrawMessageDto : " + withdrawMessageDto);

            accountService.finishWithdraw(withdrawMessageDto);
        };
    }

}
