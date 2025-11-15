package com.mechanical_workshop_usm.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Formato estándar de error de la API")
public record ApiResponse(

        @Schema(description = "Código HTTP", example = "400")
        int code,

        @Schema(description = "Mensaje principal del error", example = "Validation failed")
        String message,

        @Schema(description = "Lista de errores por campo")
        @JsonProperty("errors")
        List<FieldErrorResponse> errors
) {}
