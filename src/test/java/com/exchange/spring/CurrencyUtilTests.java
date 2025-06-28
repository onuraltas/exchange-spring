package com.exchange.spring;

import com.exchange.spring.util.CurrencyUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrencyUtilTests {

    @Test
    void isValidCurrencyUSD() {
        assertTrue(CurrencyUtil.isValidCurrency("USD"));
    }

    @Test
    void isValidCurrencyTRY() {
        assertTrue(CurrencyUtil.isValidCurrency("TRY"));
    }

    @Test
    void isInvalidCurrencyEUR() {
        assertFalse(CurrencyUtil.isValidCurrency("EUR"));
    }

}
