package com.blogapis.blogapi.exception;

import com.blogapis.blogapi.payload.BaseResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponseDTO> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
         String message = ex.getMessage();
         BaseResponseDTO res = new BaseResponseDTO(false, message);
         return new ResponseEntity<BaseResponseDTO>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException (MethodArgumentNotValidException ex) {
        Map<String, String> res = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            res.put(fieldName, message);
        });

        return new ResponseEntity<Map<String, String>>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<BaseResponseDTO> emailNotFoundExceptionHandler(EmailNotFoundException ex) {
        String message = ex.getMessage();
        BaseResponseDTO res = new BaseResponseDTO(false, message);

        return new ResponseEntity<BaseResponseDTO>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDetailsException.class)
    public ResponseEntity<BaseResponseDTO> invalidDetailsExceptionHandler(InvalidDetailsException ex) {
        String message = ex.getMessage();
        BaseResponseDTO res = new BaseResponseDTO(false, message);

        return new ResponseEntity<BaseResponseDTO>(res, HttpStatus.BAD_REQUEST);
    }
}