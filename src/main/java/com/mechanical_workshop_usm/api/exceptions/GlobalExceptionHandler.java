package com.mechanical_workshop_usm.api.exceptions;


import com.mechanical_workshop_usm.api.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultiFieldException.class)
    public ResponseEntity<ApiResponse> multiFieldException(MultiFieldException e) {

        ApiResponse body = new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                e.getErrors()
                );

        return ResponseEntity.badRequest().body(body);
    }

}
