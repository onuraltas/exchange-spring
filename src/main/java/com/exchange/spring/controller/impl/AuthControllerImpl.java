package com.exchange.spring.controller.impl;

import com.exchange.spring.controller.AuthController;
import com.exchange.spring.dto.request.UserLoginRequestDto;
import com.exchange.spring.dto.request.UserRegisterRequestDto;
import com.exchange.spring.dto.response.UserLoginResponseDto;
import com.exchange.spring.dto.response.UserRegisterResponseDto;
import com.exchange.spring.enums.ExceptionMessage;
import com.exchange.spring.exception.CustomizedException;
import com.exchange.spring.security.JwtTokenProvider;
import com.exchange.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;

    @Override
    public Mono<ResponseEntity<UserLoginResponseDto>> login(Mono<UserLoginRequestDto> requestDto) {
        return requestDto
                .flatMap(login -> this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()))
                        .map(this.tokenProvider::createToken))
                .map(jwt -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
                    return new ResponseEntity<>(new UserLoginResponseDto(jwt), httpHeaders, HttpStatus.CREATED);
                });
    }

    @Override
    public ResponseEntity<UserRegisterResponseDto> register(UserRegisterRequestDto requestDto) {

        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            throw new CustomizedException(ExceptionMessage.PASSWORDS_DO_NOT_MATCH);
        }

        var createdUserDto = userService.createUser(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());

        return new ResponseEntity<>(new UserRegisterResponseDto(createdUserDto), HttpStatus.CREATED);
    }
}
