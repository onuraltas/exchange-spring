package com.exchange.spring.controller;

import com.exchange.spring.dto.request.ExchangeRequestDto;
import com.exchange.spring.dto.response.ExchangeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RequestMapping("/rest/exchange")
public interface ExchangeController {

    @PostMapping
    Mono<ResponseEntity<ExchangeResponseDto>> exchange(@Validated @RequestBody ExchangeRequestDto requestDto);

}
