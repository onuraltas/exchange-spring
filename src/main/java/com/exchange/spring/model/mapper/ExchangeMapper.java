package com.exchange.spring.model.mapper;

import com.exchange.spring.model.dto.ExchangeDto;
import com.exchange.spring.model.entity.Exchange;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExchangeMapper {

    Exchange toEntity(ExchangeDto dto);

    ExchangeDto toDto(Exchange entity);

}
