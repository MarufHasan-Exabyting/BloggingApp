package com.example.UserDemo.exception;

public class BlogPostCreateException extends RuntimeException{
    public BlogPostCreateException(String message) {
        super(message);
    }
}
