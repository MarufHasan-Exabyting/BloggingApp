package com.example.BloggingApplication.exception;

public class UserProfileNotFoundException extends RuntimeException{
    public UserProfileNotFoundException(String message) {
        super(message);
    }
}
