package com.backend.qualifyng.backendqualifyng.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationException(HttpServletResponse response) throws IOException {
       
        return new ResponseEntity<String>("erro400", HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> badRequestViolationException(HttpServletResponse response) throws IOException {
       
        return new ResponseEntity<String>("erro 400", HttpStatus.BAD_REQUEST);

    }
    
}
