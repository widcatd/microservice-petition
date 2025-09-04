package com.petition.api.exception;

import lombok.Getter;

@Getter
public class IdentityDocumentNotFoundException extends RuntimeException {
    private final String code;
    private final String message;

    public IdentityDocumentNotFoundException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
