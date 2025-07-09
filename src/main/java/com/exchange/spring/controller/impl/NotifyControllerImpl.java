package com.exchange.spring.controller.impl;

import com.exchange.spring.controller.NotifyController;
import com.exchange.spring.model.dto.event.CommonServerEventDto;
import com.exchange.spring.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class NotifyControllerImpl implements NotifyController {

    private final NotifyService notifyService;

    @Override
    public Flux<CommonServerEventDto> getStream() {
        return notifyService.getCommonServerEventStream();
    }

}
