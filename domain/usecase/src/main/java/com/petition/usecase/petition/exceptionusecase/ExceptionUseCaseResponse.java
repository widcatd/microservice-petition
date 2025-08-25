package com.petition.usecase.petition.exceptionusecase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionUseCaseResponse {
    INTERNAL_SERVER_ERROR("ERR-500", "error.internal.server"),
    NO_DATA_FOUND("ERR-001", "No data found for the requested petition"),
    VALIDATION_ERROR("ERR-003", "Validation failed for the request"),
    DATA_ALREADY_EXISTS("ERR-004","The program with ID %d already has an assigned user"),
    ID_USER_NOT_FOUND("ERR-006","ID_USER %d not found"),
    EMAIL_ALREADY_EXIST("ERR-007","El email %s ya se encuentra registrado"),
    ID_USER_ALREADY_EXISTS("ERR-008", "The program already has an assigned ID_USER %d");
    private final String code;
    private final String message;
}
