package com.backend.qualifyng.backendqualifyng.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.backend.qualifyng.backendqualifyng.exceptions.ResourceNotFoundException;
import com.backend.qualifyng.backendqualifyng.responses.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler({ConstraintViolationException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponse> badRequestViolationException(HttpServletResponse response) throws IOException {
        return new ResponseEntity<ErrorResponse>(getErrorResponse(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> resourceNotFoundException(HttpServletResponse response) throws IOException {
       
        ErrorResponse errorResponse = ErrorResponse.builder().message("Recurso não encontrado!")
                .statusDesc(HttpStatus.NOT_FOUND.name()).build();

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);

    }

    
    private ErrorResponse getErrorResponse() {
        ErrorResponse errorResponse = ErrorResponse.builder().message("Campo obrigatório!")
                .statusDesc(HttpStatus.BAD_REQUEST.name()).build();
        return errorResponse;
    }

}
