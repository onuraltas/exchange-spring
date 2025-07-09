package com.exchange.spring.config;

import com.exchange.spring.model.CommonServerEvent;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Sinks;

@EntityScan("com.exchange.spring.model.entity")
@EnableJpaRepositories("com.exchange.spring.repository.jpa")
@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Sinks.Many<CommonServerEvent> commonServerEventEmitSink() {
        return Sinks.many().replay().limit(1);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

}
