package com.nonylog.global.exception;

public class AlreadyExistsEmailException extends NonylogException {

    public static String MESSAGE = "이미 가입된 이메일입니다.";

    public AlreadyExistsEmailException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
