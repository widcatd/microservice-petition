package com.petition.model.exception;

import lombok.Getter;

@Getter
public class DataAlreadyExistException extends RuntimeException {
    private final String code;
    private final String message;

    public DataAlreadyExistException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
