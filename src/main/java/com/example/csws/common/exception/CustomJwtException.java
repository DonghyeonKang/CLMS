package com.example.csws.common.exception;

public class CustomJwtException extends RuntimeException {
    private final JwtErrorCode errorCode;

    public CustomJwtException(JwtErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
