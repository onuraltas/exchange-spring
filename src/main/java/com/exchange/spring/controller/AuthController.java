package com.exchange.spring.controller;

import com.exchange.spring.dto.request.UserLoginRequestDto;
import com.exchange.spring.dto.request.UserRegisterRequestDto;
import com.exchange.spring.dto.response.UserLoginResponseDto;
import com.exchange.spring.dto.response.UserRegisterResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RequestMapping("/rest/auth")
public interface AuthController {

    @PostMapping("/login")
    Mono<ResponseEntity<UserLoginResponseDto>> login(@Valid @RequestBody Mono<UserLoginRequestDto> requestDto);

    @PostMapping("/register")
    ResponseEntity<UserRegisterResponseDto> register(@Valid @RequestBody UserRegisterRequestDto requestDto);

}
