package com.springboot.simplyfly.exception;

public class UnauthorizedAccessException extends RuntimeException{
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}