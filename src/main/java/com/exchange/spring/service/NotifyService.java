package com.exchange.spring.service;

import com.exchange.spring.dto.event.CommonServerEventDto;
import reactor.core.publisher.Flux;

public interface NotifyService {

    Flux<CommonServerEventDto> getCommonServerEventStream();

}
