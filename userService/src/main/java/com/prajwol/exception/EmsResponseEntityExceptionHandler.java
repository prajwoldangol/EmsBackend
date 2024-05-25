package com.prajwol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EmsResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmsCustomException.class)
    public ResponseEntity<EmsCustomErrorResponse> handleExceptions(EmsCustomException emsCustomException){
        return new ResponseEntity<>(new EmsCustomErrorResponse().builder()
                .errorCode(emsCustomException.getErrorCode())
                .errorMessage(emsCustomException.getMessage())
                .build(), HttpStatus.UNAUTHORIZED);
    }
}
