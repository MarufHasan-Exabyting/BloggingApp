package com.example.UserDemo.exception;

public class BlogPostNotFoundException extends RuntimeException{
    public BlogPostNotFoundException(String message) {
        super(message);
    }
}
