package com.exchange.spring.controller.impl;

import com.exchange.spring.controller.ExchangeController;
import com.exchange.spring.model.dto.request.ExchangeRequestDto;
import com.exchange.spring.model.dto.response.ExchangeResponseDto;
import com.exchange.spring.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ExchangeControllerImpl implements ExchangeController {

    private final ExchangeService exchangeService;

    @Override
    public Mono<ResponseEntity<ExchangeResponseDto>> exchange(ExchangeRequestDto requestDto) {

        return exchangeService.exchange(requestDto).map(ResponseEntity::ok);

    }

}
