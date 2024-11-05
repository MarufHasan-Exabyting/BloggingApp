package com.example.BloggingApplication.exception;

public class BlogPostCreateException extends RuntimeException{
    public BlogPostCreateException(String message) {
        super(message);
    }
}
