package com.exchange.spring.mapper;

import com.exchange.spring.dto.ExchangeDto;
import com.exchange.spring.entity.Exchange;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExchangeMapper {

    Exchange toEntity(ExchangeDto dto);

    ExchangeDto toDto(Exchange entity);

}
