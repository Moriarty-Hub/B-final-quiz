package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

//TODO GTB：少了处理404的Exception
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        Error error = Error.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage())
                .build();
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(InsufficientTrainersException.class)
    public ResponseEntity<Error> insufficientTrainersExceptionHandler(InsufficientTrainersException exception) {
        Error error = Error.builder()
                .status(exception.getHttpStatus().value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(error);
    }
}
