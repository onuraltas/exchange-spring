package com.exchange.spring.service.impl;

import com.exchange.spring.dto.response.ExchangeRateResponseDto;
import com.exchange.spring.model.ExchangeRateTemp;
import com.exchange.spring.repository.redis.ExhangeRateTempRepository;
import com.exchange.spring.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExhangeRateTempRepository exhangeRateTempRepository;
    private final WebClient webClient;

    public Mono<Map<String, BigDecimal>> getExchangeRate(String sourceCurrencyCode) {
        var optionalExchangeRateTemp = exhangeRateTempRepository.findById(sourceCurrencyCode);

        return optionalExchangeRateTemp
                .map(exchangeRateTemp -> Mono.just(exchangeRateTemp.getConversionRates()))
                .orElseGet(() -> getExchangeRateFromWeb(sourceCurrencyCode).map(exchangeRateResponseDto -> {
                    var exchangeRateTemp = new ExchangeRateTemp();
                    exchangeRateTemp.setBaseCode(sourceCurrencyCode);
                    exchangeRateTemp.setConversionRates(exchangeRateResponseDto.getConversion_rates());
                    exhangeRateTempRepository.save(exchangeRateTemp);

                    return exchangeRateResponseDto.getConversion_rates();
                }));

    }

    public Mono<BigDecimal> getExchangeRate(String sourceCurrencyCode, String targetCurrencyCode) {
        var optionalExchangeRateTemp = exhangeRateTempRepository.findById(sourceCurrencyCode);

        return optionalExchangeRateTemp
                .map(exchangeRateTemp -> Mono.just(exchangeRateTemp.getConversionRates().get(targetCurrencyCode)))
                .orElseGet(() -> getExchangeRateFromWeb(sourceCurrencyCode).map(exchangeRateResponseDto -> {
                    var exchangeRateTemp = new ExchangeRateTemp();
                    exchangeRateTemp.setBaseCode(sourceCurrencyCode);
                    exchangeRateTemp.setConversionRates(exchangeRateResponseDto.getConversion_rates());
                    exhangeRateTempRepository.save(exchangeRateTemp);

                    return exchangeRateResponseDto.getConversion_rates().get(targetCurrencyCode);
                }));

    }

    private Mono<ExchangeRateResponseDto> getExchangeRateFromWeb(String currencyCode) {
        var uri = String.format("https://v6.exchangerate-api.com/v6/9ad0e75aa2c862bec3574812/latest/%s", currencyCode);
        return webClient.get().uri(uri)
                .header("invocationFrom", "WebClient")
                .retrieve()
                .bodyToMono(ExchangeRateResponseDto.class);
    }

}
