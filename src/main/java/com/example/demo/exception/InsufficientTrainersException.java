package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class InsufficientTrainersException extends RuntimeException {

    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    @Override
    public String getMessage() {
        return "The number of trainers is insufficient";
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
