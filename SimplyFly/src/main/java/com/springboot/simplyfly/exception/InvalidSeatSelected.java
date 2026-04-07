package com.springboot.simplyfly.exception;

public class InvalidSeatSelected extends RuntimeException{
    public InvalidSeatSelected(String message) {
        super(message);
    }
}