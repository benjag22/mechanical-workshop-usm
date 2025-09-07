package com.mechanical_workshop_usm.api.exceptions;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import java.util.List;
import lombok.Getter;

@Getter
public class MultiFieldException extends RuntimeException {

    private final List<FieldErrorResponse> errors;

    public MultiFieldException(String message, List<FieldErrorResponse> errors) {
        super(message);
        this.errors = errors;
    }

}
