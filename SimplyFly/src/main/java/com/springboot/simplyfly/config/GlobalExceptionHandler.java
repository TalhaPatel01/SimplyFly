package com.springboot.simplyfly.config;

import com.springboot.simplyfly.exception.InvalidSeatSelected;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.exception.SeatsNotAvailableException;
import com.springboot.simplyfly.exception.UnauthorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> getResourceNotFoundException(ResourceNotFoundException e){
        Map<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(SeatsNotAvailableException.class)
    public ResponseEntity<?> getSeatsNotAvailableException(SeatsNotAvailableException e){
        Map<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(InvalidSeatSelected.class)
    public ResponseEntity<?> getInvalidSeatSelected(InvalidSeatSelected e){
        Map<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<?> getUnauthorizedAccessException(UnauthorizedAccessException e){
        Map<String,String> map = new HashMap<>();
        map.put("message",e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }
}