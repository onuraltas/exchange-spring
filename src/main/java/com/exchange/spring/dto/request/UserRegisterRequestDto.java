package com.exchange.spring.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegisterRequestDto {

    @NotEmpty(message = "Missing required field - Email")
    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty(message = "Missing required field - Name")
    private String name;

    @NotBlank(message = "Missing required field - Password")
    private String password;

    @NotBlank(message = "Missing required field - Password confirm")
    private String passwordConfirm;

}
