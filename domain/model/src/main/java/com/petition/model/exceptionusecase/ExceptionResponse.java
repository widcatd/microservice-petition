package com.petition.model.exceptionusecase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionResponse {
    INTERNAL_SERVER_ERROR("ERR-500", "error.internal.server"),
    NO_DATA_FOUND("ERR-001", "No data found for the requested petition"),
    VALIDATION_ERROR("ERR-003", "Validation failed for the request"),
    DATA_ALREADY_EXISTS("ERR-004","The program with ID %d already has an assigned user"),
    ID_USER_NOT_FOUND("ERR-006","ID_USER %d not found"),
    ID_USER_ALREADY_EXISTS("ERR-007", "The program already has an assigned ID_USER %d"),
    IDENTITY_DOCUMENT_NOT_FOUND("ERR-008", "No existe un usuario con el documento %s"),
    PERMISSION_DENIED("ERR-403", "Solo puedes crear solicitudes de préstamo para ti mismo");
    private final String code;
    private final String message;
}
