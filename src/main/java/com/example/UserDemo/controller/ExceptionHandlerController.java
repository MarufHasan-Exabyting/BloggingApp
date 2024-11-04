package com.example.UserDemo.controller;

import com.example.UserDemo.exception.ErrorDetails;
import com.example.UserDemo.exception.ResourceNotFoundException;
import com.example.UserDemo.exception.UserNotFoundException;
import com.example.UserDemo.exception.UserProfileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler
    public ResponseEntity<ResourceNotFoundException> handleException(ResourceNotFoundException exception)
    {
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserNotFoundException> handleException(UserNotFoundException userNotFoundException)
    {
        return new ResponseEntity<>(userNotFoundException,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserProfileNotFoundException> handleException(UserProfileNotFoundException userProfileNotFoundException)
    {
        return new ResponseEntity<>(userProfileNotFoundException,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException)
    {
        Map<String,String> errorMap = new HashMap<>();
        ErrorDetails errorDetails = new ErrorDetails();
        List<String> errors = methodArgumentNotValidException.getAllErrors().stream().map(
                ObjectError::toString).toList();
        errorDetails.setErrors(errors);
        errorDetails.setMessage("Validation Failed");
        errorDetails.setStatus(HttpStatus.BAD_REQUEST);
        errorMap.putIfAbsent("Error",errorDetails.toString());
        return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
    }
}
