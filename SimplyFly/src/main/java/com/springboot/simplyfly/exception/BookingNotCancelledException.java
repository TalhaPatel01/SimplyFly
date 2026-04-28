package com.springboot.simplyfly.exception;

public class BookingNotCancelledException extends RuntimeException{
    public BookingNotCancelledException(String message) {
        super(message);
    }
}