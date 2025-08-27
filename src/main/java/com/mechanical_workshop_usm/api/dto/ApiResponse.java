package com.mechanical_workshop_usm.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ApiResponse(
        int code,
        String message,
        @JsonProperty("errors") List<FieldErrorResponse> errors
        ) {
}
