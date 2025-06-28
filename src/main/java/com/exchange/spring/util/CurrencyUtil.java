package com.exchange.spring.util;

import java.util.AbstractMap;
import java.util.Map;

public class CurrencyUtil {

    private static final Map<String, String> allowedCurrencyMap = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("TRY", "TRY"),
            new AbstractMap.SimpleEntry<>("USD", "USD")
    );

    public static boolean isValidCurrency(String currencyName) {
        return allowedCurrencyMap.get(currencyName) != null;
    }

}
