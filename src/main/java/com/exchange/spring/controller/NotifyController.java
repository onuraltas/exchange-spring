package com.exchange.spring.controller;

import com.exchange.spring.model.dto.event.CommonServerEventDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

@RequestMapping("/rest/notify")
public interface NotifyController {

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<CommonServerEventDto> getStream();

}
