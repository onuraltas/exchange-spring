package com.exchange.spring.functions;

import com.exchange.spring.dto.message.TransactionMessageDto;
import com.exchange.spring.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class AccountFunctions {

    @Bean
    public Consumer<TransactionMessageDto> transactionMessage(AccountService accountService) {
        return transactionMessageDto -> {
            log.info("transactionMessageDto : " + transactionMessageDto);

            accountService.finishTransaction(transactionMessageDto);
        };
    }

}
