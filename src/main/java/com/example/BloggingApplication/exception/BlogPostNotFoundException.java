package com.example.BloggingApplication.exception;

public class BlogPostNotFoundException extends RuntimeException{
    public BlogPostNotFoundException(String message) {
        super(message);
    }
}
