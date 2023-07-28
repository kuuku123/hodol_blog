package com.tony.test_blog.exception;

public class InvalidSignInInformationException extends BlogException{

    private static final String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";

    public InvalidSignInInformationException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
