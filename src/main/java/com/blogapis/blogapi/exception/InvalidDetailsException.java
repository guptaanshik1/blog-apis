package com.blogapis.blogapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidDetailsException extends RuntimeException {

    private String details;

    public InvalidDetailsException(String details) {
        super(String.format("Username or Password does not match"));
        this.details = details;
    }

}