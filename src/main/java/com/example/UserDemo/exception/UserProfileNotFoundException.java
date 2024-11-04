package com.example.UserDemo.exception;

public class UserProfileNotFoundException extends RuntimeException{
    public UserProfileNotFoundException(String message) {
        super(message);
    }
}
