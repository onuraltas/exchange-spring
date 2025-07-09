package com.exchange.spring.model.dto.response;

import com.exchange.spring.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponseDto {

    private UserDto user;

}
