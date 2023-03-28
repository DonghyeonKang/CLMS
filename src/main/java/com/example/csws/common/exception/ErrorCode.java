package com.example.csws.common.exception;

public enum ErrorCode {

    USER_NOT_FOUND(400, "해당 유저를 찾을 수 없습니다."),

    DUPLICATED_EMAIL(400, "이미 존재하는 E-mail입니다."),
    DUPLICATED_NICKNAME(400, "이미 존재하는 Ninkname입니다."),

    FAIL_VERIFY(400, "인증 번호가 틀렸습니다."),
    NOT_VERIFIED_PHONE(400, "인증되지 않은 번호입니다."),
    NOT_SEND_MESSAGE(400, "인증 번호가 전송되지 않았습니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
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
