package com.exchange.spring.mapper;

import com.exchange.spring.dto.UserDto;
import com.exchange.spring.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User entity);

}
