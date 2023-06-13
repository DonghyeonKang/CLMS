package com.example.csws.common.exception;

public enum JwtErrorCode {
    JWT_ACCESS_NOT_EXIST(403, "Access token 이 존재하지 않습니다. "),
    JWT_REFRESH_NOT_EXIST(403, "Refresh token 이 존재하지 않습니다. "),
    JWT_REFRESH_NOT_VALID(403, "Refresh token 이 유효하지 않습니다. "),
    JWT_ACCESS_NOT_VALID(403, "Access token 이 유효하지 않습니다. ");
    private final int status;
    private final String message;

    JwtErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
}
