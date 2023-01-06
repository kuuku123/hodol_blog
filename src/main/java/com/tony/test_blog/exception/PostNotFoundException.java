package com.tony.test_blog.exception;

public class PostNotFoundException extends RuntimeException{
    private static final String MESSAGE = "존재하지 않는 글입니다.";
    public PostNotFoundException() {
        super(MESSAGE);
    }

    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
