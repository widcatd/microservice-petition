package com.petition.model.exception;

import lombok.Getter;

@Getter
public class PermissionDeniedException extends RuntimeException {
    private final String code;
    private final String message;

    public PermissionDeniedException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}