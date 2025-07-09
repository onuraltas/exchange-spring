package com.exchange.spring.model.mapper;

import com.exchange.spring.model.dto.UserDto;
import com.exchange.spring.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User entity);

}
