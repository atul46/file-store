package com.nokia.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity controlDataServiceException(
            final HttpServletRequest httpServletRequest, final RuntimeException e) throws IOException {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}