package com.exchange.spring.service;

import com.exchange.spring.dto.UserDto;

public interface UserService {

    UserDto createUser(String name, String email, String password);

}
