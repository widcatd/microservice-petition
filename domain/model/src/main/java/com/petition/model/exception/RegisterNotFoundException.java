package com.petition.model.exception;

import lombok.Getter;

@Getter
public class RegisterNotFoundException extends RuntimeException {
    private final String code;
    private final String message;

    public RegisterNotFoundException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
