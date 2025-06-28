package com.exchange.spring.exception;

import com.exchange.spring.enums.ExceptionMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CustomizedResponseEntityExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public Mono<ResponseEntity<ProblemDetail>> handleOtherExceptions(Exception ex, ServerWebExchange exchange) {

        log.error("ERROR", ex);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle(ExceptionMessage.COMMON_ERROR.getErrorMessage());
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setInstance(exchange.getRequest().getURI());

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail));
    }

    @ExceptionHandler(value = {CustomizedException.class})
    public Mono<ResponseEntity<ProblemDetail>> handleCustomizedException(CustomizedException ex,
                                                                         ServerWebExchange exchange) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(ExceptionMessage.COMMON_ERROR.getErrorMessage());
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setInstance(exchange.getRequest().getURI());

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail));
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public Mono<ResponseEntity<ProblemDetail>> handleCustomizedException(UsernameNotFoundException ex,
                                                                         ServerWebExchange exchange) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle(ExceptionMessage.BAD_CREDENTIALS.getErrorMessage());
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setInstance(exchange.getRequest().getURI());

        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail));
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public Mono<ResponseEntity<ProblemDetail>> handleCustomizedException(BadCredentialsException ex,
                                                                         ServerWebExchange exchange) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle(ExceptionMessage.BAD_CREDENTIALS.getErrorMessage());
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setInstance(exchange.getRequest().getURI());

        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public Mono<ResponseEntity<ProblemDetail>> handleCustomizedException(ConstraintViolationException ex,
                                                                         ServerWebExchange exchange) {

        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(ExceptionMessage.VALIDATION_ERROR.getErrorMessage());
        problemDetail.setDetail(errorMessage);
        problemDetail.setInstance(exchange.getRequest().getURI());

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(WebExchangeBindException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatusCode status,
                                                                          ServerWebExchange exchange) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                Objects.requireNonNull(ex.getBody().getDetail()));
        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> problemDetail.setProperty(fieldError.getField(), fieldError.getDefaultMessage()));

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleServerWebInputException(ServerWebInputException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatusCode status,
                                                                         ServerWebExchange exchange) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getCause().getMessage());

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail));
    }

}
