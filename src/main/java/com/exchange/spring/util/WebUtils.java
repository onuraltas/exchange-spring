package com.exchange.spring.util;

import com.exchange.spring.entity.User;
import com.exchange.spring.enums.ExceptionMessage;
import com.exchange.spring.exception.CustomizedException;
import com.exchange.spring.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class WebUtils {

    private final UserRepository userRepository;

    public Mono<User> getCurrentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName)
                .map(username -> userRepository.findByEmail(username)
                        .orElseThrow(() -> new CustomizedException(ExceptionMessage.NO_RECORD_FOUND)));
    }

}
