package com.petition.model.exception;

import lombok.Getter;

@Getter
public class JwtAuthenticationException extends RuntimeException {
  private final String code;
  private final String message;

  public JwtAuthenticationException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }
}
