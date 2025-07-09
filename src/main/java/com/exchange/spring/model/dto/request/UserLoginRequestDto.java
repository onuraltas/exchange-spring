package com.exchange.spring.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserLoginRequestDto {

    @NotEmpty(message = "Missing required field - Email")
    private String email;

    @NotEmpty(message = "Missing required field - Password")
    private String password;

}
