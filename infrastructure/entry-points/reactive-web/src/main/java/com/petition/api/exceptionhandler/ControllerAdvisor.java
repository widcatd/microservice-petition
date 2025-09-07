package com.petition.api.exceptionhandler;

import com.petition.model.exception.*;
import com.petition.model.exceptionusecase.ExceptionResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Component
public class ControllerAdvisor {
    public Mono<ServerResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> details = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(
                ExceptionResponse.VALIDATION_ERROR.getCode(),
                ExceptionResponse.VALIDATION_ERROR.getMessage(),
                details
        );

        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }
    public Mono<ServerResponse> handleDataAlreadyExistsException(DataAlreadyExistException ex, ServerRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getCode(),
                ex.getMessage(),
                request.path()
        );

        return ServerResponse.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }
    public Mono<ServerResponse> handleDataAlreadyExistsException(IdentityDocumentNotFoundException ex, ServerRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getCode(),
                ex.getMessage(),
                request.path()
        );
        return ServerResponse.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }
    public Mono<ServerResponse> handleDataAlreadyExistsException(RegisterNotFoundException ex, ServerRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                request.path()
        );
        return ServerResponse.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }
    public Mono<ServerResponse> handleDataAlreadyExistsException(PetitionValidationException ex, ServerRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                request.path()
        );
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }
    public Mono<ServerResponse> handleDataAlreadyExistsException(PermissionDeniedException ex, ServerRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                request.path()
        );
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }
    public Mono<ServerResponse> handleDataAlreadyExistsException(DataNotFoundException ex, ServerRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getCode(),
                ex.getMessage(),
                request.path()
        );
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }
}