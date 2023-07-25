package com.tony.test_blog.exception;

public class UnauthorizedException extends BlogException{
    private static final String MESSAGE = "인증이 필요합니다.";
    public UnauthorizedException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
