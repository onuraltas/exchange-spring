package com.exchange.spring.service.impl;

import com.exchange.spring.dto.event.CommonServerEventDto;
import com.exchange.spring.mapper.AccountMapper;
import com.exchange.spring.model.CommonServerEvent;
import com.exchange.spring.service.NotifyService;
import com.exchange.spring.util.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RequiredArgsConstructor
@Transactional
@Service
public class NotifyServiceImpl implements NotifyService {

    private final WebUtils webUtils;
    private final Sinks.Many<CommonServerEvent> commonServerEventEmitSink;
    private final AccountMapper accountMapper;

    @Override
    public Flux<CommonServerEventDto> getCommonServerEventStream() {
        return this.commonServerEventEmitSink
                .asFlux()
                .filterWhen(commonServerEvent ->
                        webUtils.getCurrentUser().map(currentUser -> currentUser.getUserId().equals(commonServerEvent.getUserId())))
                .map(commonServerEvent ->
                        new CommonServerEventDto(accountMapper.toDto(commonServerEvent.getAccount()), commonServerEvent.getMessage()));
    }

}
