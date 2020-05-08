package com.ynz.asyncapi.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RuntimeException> handleNotFoundException(NotFoundException e) {
        log.warn(e.getMessage(), e);
        return new ResponseEntity(e, HttpStatus.NOT_FOUND);
    }

}
