package com.springboot.simplyfly.config;

import com.springboot.simplyfly.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    final static String key = "message";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String>> getResourceNotFoundException(ResourceNotFoundException e){
        log.atLevel(Level.WARN).log(e.getMessage());

        log.atLevel(Level.INFO).log("Check the ID given to the API");

        Map<String,String> map = new HashMap<>();
        map.put(key,e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(SeatsNotAvailableException.class)
    public ResponseEntity<Map<String,String>> getSeatsNotAvailableException(SeatsNotAvailableException e){
        log.atLevel(Level.WARN).log(e.getMessage());

        log.atLevel(Level.INFO).log("Seats unavailable...");
        Map<String,String> map = new HashMap<>();
        map.put(key,e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(InvalidSeatSelected.class)
    public ResponseEntity<Map<String,String>> getInvalidSeatSelected(InvalidSeatSelected e){
        log.atLevel(Level.WARN).log(e.getMessage());

        log.atLevel(Level.INFO).log("Invalid Seat selected...");
        Map<String,String> map = new HashMap<>();
        map.put(key,e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String,String>> getUnauthorizedAccessException(UnauthorizedAccessException e){
        log.atLevel(Level.WARN).log(e.getMessage());

        log.atLevel(Level.INFO).log("Access not granted to perform this operation...");
        Map<String,String> map = new HashMap<>();
        map.put(key,e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(BookingNotCancelledException.class)
    public ResponseEntity<Map<String,String>> getBookingNotCancelledException(BookingNotCancelledException e){
        log.atLevel(Level.WARN).log(e.getMessage());

        log.atLevel(Level.INFO).log("Unable to cancel booking");
        Map<String,String> map = new HashMap<>();
        map.put(key,e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,Object>> handleRuntimeException(
            RuntimeException e
    ){
        System.out.println("called...");
        Map<String,Object> map = new HashMap<>();
        map.put(key, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(map);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String,Object>> handleIOException(
            IOException e
    ){

        Map<String,Object> map = new HashMap<>();
        map.put(key, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(map);
    }
}