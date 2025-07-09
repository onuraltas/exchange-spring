package com.exchange.spring.model.mapper;

import com.exchange.spring.model.dto.AccountDto;
import com.exchange.spring.model.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(AccountDto dto);

    AccountDto toDto(Account entity);

}
