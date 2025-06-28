package com.exchange.spring.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    USER_NOT_SIGNED_IN("10001", "Please sign-in"),
    MISSING_REQUIRED_FIELD("10002", "Missing required field. Please check documentation for required fields"),
    RECORD_ALREADY_EXISTS("10003", "Record already exists"),
    INTERNAL_SERVER_ERROR("10004", "Internal server error"),
    NO_RECORD_FOUND("10005", "Record with provided id is not found"),
    AUTHENTICATION_FAILED("10006", "Authentication failed"),
    AUTHORIZATION_FAILED("10007", "Authorization failed"),
    COULD_NOT_UPDATE_RECORD("10009", "Could not update record"),
    COULD_NOT_DELETE_RECORD("10010", "Could not delete record"),
    PASSWORDS_DO_NOT_MATCH("10016", "Passwords do not match"),
    EMAIL_ALREADY_USED("10018", "Email already used"),
    UNKNOWN_CURRENCY("10031", "Unknown currency (%s)"),
    SAME_CURRENCY("10032", "Same currency cannot be traded"),
    NOT_ENOUGH_BALANCE("10033", "Not enough balance"),
    BAD_CREDENTIALS("10097", "Bad credentials"),
    VALIDATION_ERROR("10098", "Validation error"),
    COMMON_ERROR("10099", "Some error happened");

    private final String errorCode;
    private final String errorMessage;

}
