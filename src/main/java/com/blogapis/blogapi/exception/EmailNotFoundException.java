package com.blogapis.blogapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmailNotFoundException extends RuntimeException {
    private String username;
    private String fieldName;
    private String fieldValue;

    public EmailNotFoundException(String username, String fieldName, String fieldValue) {
        super(String.format("There is no user with %s with %s : %s ", username, fieldName, fieldValue));
        this.username = username;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
