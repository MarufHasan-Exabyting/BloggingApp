package com.example.BloggingApplication.exception;

public class UserCreateException extends RuntimeException{
    public UserCreateException(String message) {
        super(message);
    }
}
