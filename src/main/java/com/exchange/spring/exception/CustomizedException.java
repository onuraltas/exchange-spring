package com.exchange.spring.exception;

import com.exchange.spring.enums.ExceptionMessage;
import lombok.Data;

@Data
public class CustomizedException extends RuntimeException {

    private String code;

    public CustomizedException(String code, String message) {
        super(message);
        this.code = code;
    }

    public CustomizedException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getErrorMessage());
        this.code = exceptionMessage.getErrorCode();
    }

    public CustomizedException(ExceptionMessage exceptionMessage, Object... values) {
        super(exceptionMessage.getErrorMessage().formatted(values));
    }

}
