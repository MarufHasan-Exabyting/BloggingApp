package com.example.BloggingApplication.controller;

import com.example.BloggingApplication.dto.ApiResponse;
import com.example.BloggingApplication.exception.*;
import com.example.BloggingApplication.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<Object> handleException(ResourceNotFoundException exception, HttpServletRequest request)
    {
        return ResponseUtil.error(Arrays.asList(exception.getMessage()),"Exception occured",400, request.getRequestURI());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException, HttpServletRequest request)
    {
        return  ResponseUtil.error(Arrays.asList(userNotFoundException.getMessage()),"User not Found ", 404, request.getRequestURI());
    }

    @ExceptionHandler(UserProfileNotFoundException.class)
    public ApiResponse<Object> handleException(UserProfileNotFoundException userProfileNotFoundException, HttpServletRequest request)
    {
        return  ResponseUtil.error(Arrays.asList(userProfileNotFoundException.getMessage()),"UserProfile not Found ", 404, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest request)
    {
        //System.out.println("Validation Error");
        List<String> errors = new ArrayList<>();

        methodArgumentNotValidException.getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        return ResponseUtil.error(errors,"Validation Failed",400, request.getRequestURI());
    }

    @ExceptionHandler(BlogPostNotFoundException.class)
    public ApiResponse<Object> handleBlogPostNotFoundException(BlogPostNotFoundException blogPostNotFoundException, HttpServletRequest request)
    {
        return ResponseUtil.error(Arrays.asList(blogPostNotFoundException.getMessage()),"Blog post not found exception",400, request.getRequestURI());
    }

    @ExceptionHandler(UserCreateException.class)
    public ApiResponse<Object> handleUserCreateException(UserCreateException userCreateException, HttpServletRequest request)
    {
        return ResponseUtil.error(Arrays.asList(userCreateException.getMessage()),"User Creation failed",400, request.getRequestURI());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ApiResponse<Object> handleCommentNotFoundException(CommentNotFoundException commentNotFoundException, HttpServletRequest request)
    {
        return ResponseUtil.error(Arrays.asList(commentNotFoundException.getMessage()),"Comment Not found",400, request.getRequestURI());
    }

    @ExceptionHandler(JWTAuthenticationException.class)
    public ApiResponse<Object> handleJWTAuthenticationException(JWTAuthenticationException jwtAuthenticationException, HttpServletRequest request)
    {
        return ResponseUtil.error(Arrays.asList(jwtAuthenticationException.getMessage()),"Token not authenticated",400, request.getRequestURI());
    }

    @ExceptionHandler(SignatureException.class)
    public ApiResponse<Object> handleSignatureException(SignatureException signatureException, HttpServletRequest request)
    {
        return ResponseUtil.error(Arrays.asList(signatureException.getMessage()),"Signature not verified",400, request.getRequestURI());
    }

    @ExceptionHandler(AuthorizationException.class)
    public ApiResponse<Object> handleAuthorizatioinException(AuthorizationException authorizationException, HttpServletRequest request)
    {
        return ResponseUtil.error(Arrays.asList(authorizationException.getMessage()),"do not have authorization",400, request.getRequestURI());
    }
}
