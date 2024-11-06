package com.example.BloggingApplication.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class UserNotFoundException extends RuntimeException{
    private ErrorDetails errorDetails;
    public UserNotFoundException(String message)
    {
        //super(message);
        //System.out.println(message);
        errorDetails = new ErrorDetails();
        errorDetails.setMessage(message);
        errorDetails.setStatus(HttpStatus.NOT_FOUND);
        errorDetails.setTimeStamp(new Date(System.currentTimeMillis()));
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(ErrorDetails errorDetails) {
        this.errorDetails = errorDetails;
    }
}
