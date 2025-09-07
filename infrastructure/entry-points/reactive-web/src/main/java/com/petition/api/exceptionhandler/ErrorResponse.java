package com.petition.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private int status;
    private String code;
    private String message;
    private String path;
    private List<String> details;

    public ErrorResponse(HttpStatus status, String code, String message, String path) {
        this.status = status.value();
        this.code = code;
        this.message = message;
        this.path = path;
    }
    public ErrorResponse(String code, String message, List<String> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }
    public ErrorResponse(String code, String message, String path) {
        this.code = code;
        this.message = message;
        this.path = path;
    }
}
