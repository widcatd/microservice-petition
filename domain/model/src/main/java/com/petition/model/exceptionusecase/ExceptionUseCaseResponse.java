package com.petition.model.exceptionusecase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionUseCaseResponse {
    INTERNAL_SERVER_ERROR("ERR-500", "error.internal.server"),
    NO_DATA_FOUND("ERR-001", "No data found for the requested petition"),
    VALIDATION_ERROR("ERR-003", "Validation failed for the request"),
    DATA_ALREADY_EXISTS("ERR-004","The program with ID %d already has an assigned user"),
    ID_LOAN_TYPE_NOT_FOUND("ERR-006","No existe el id solicitud prestamo %d"),
    EMAIL_ALREADY_EXIST("ERR-007","El email %s ya se encuentra registrado"),
    ID_USER_ALREADY_EXISTS("ERR-008", "The program already has an assigned ID_USER %d"),
    STATE_REGISTER_NOT_FOUND("ERR-008", "El registro de estado no existe"),

    PETITION_IDENTITY_DOCUMENT_NULL("PET-001", "Se debe agregar el documento de identidad"),
    PETITION_MOUNT_NULL("PET-002", "Se debe agregar el monto"),
    PETITION_LOAN_TERM_NULL("PET-003", "Se debe agregar el plazo"),
    PETITION_ID_LOAN_TYPE_NULL("PET-004", "Se debe agregar el id del tipo de préstamo"),

    JWT_TOKEN_NOT_FOUND("JWT-001", "El token no se encontró en la cabecera Authorization"),
    JWT_TOKEN_INVALID("JWT-002", "El token enviado es inválido"),
    JWT_TOKEN_EXPIRED("JWT-003", "El token ha expirado"),
    JWT_TOKEN_UNSUPPORTED("JWT-004", "El formato del token no es soportado"),
    JWT_TOKEN_MALFORMED("JWT-005", "El token está mal formado"),
    JWT_UNAUTHORIZED("JWT-006", "No autorizado: se requiere un token válido para acceder a este recurso");

    private final String code;
    private final String message;
}
