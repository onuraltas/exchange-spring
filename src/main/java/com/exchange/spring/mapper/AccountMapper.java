package com.exchange.spring.mapper;

import com.exchange.spring.dto.AccountDto;
import com.exchange.spring.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(AccountDto dto);

    AccountDto toDto(Account entity);

}
