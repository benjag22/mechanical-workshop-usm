package com.mechanical_workshop_usm.api.dto;

public record FieldErrorResponse(
        String field,
        String message
) {
}
