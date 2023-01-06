package com.tony.test_blog.exception;

public class PostNotFoundException extends BlogException{
    private static final String MESSAGE = "존재하지 않는 글입니다.";
    public PostNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
