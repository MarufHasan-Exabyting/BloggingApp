package com.example.BloggingApplication.exception;

public class UserProfileCreateException extends RuntimeException{
    public UserProfileCreateException(String message) {
        super(message);
    }
}
