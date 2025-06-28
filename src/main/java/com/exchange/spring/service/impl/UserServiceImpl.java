package com.exchange.spring.service.impl;

import com.exchange.spring.dto.UserDto;
import com.exchange.spring.entity.User;
import com.exchange.spring.enums.ExceptionMessage;
import com.exchange.spring.exception.CustomizedException;
import com.exchange.spring.mapper.UserMapper;
import com.exchange.spring.repository.jpa.UserRepository;
import com.exchange.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService, ReactiveUserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        try {
            User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("email not found -> " + username));

            return Mono.just(new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>()));
        } catch (UsernameNotFoundException e) {
            return Mono.error(e);
        }
    }

    @Override
    public UserDto createUser(String name, String email, String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new CustomizedException(ExceptionMessage.EMAIL_ALREADY_USED);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setEncryptedPassword(passwordEncoder.encode(password));

        var savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

}
