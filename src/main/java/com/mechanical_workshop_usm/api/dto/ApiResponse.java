package com.mechanical_workshop_usm.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Formato estándar de error de la API")
public record ApiResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED,description = "Código HTTP", example = "400")
        int code,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Mensaje principal del error", example = "Validation failed")
        String message,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Lista de errores por campo")
        List<FieldErrorResponse> errors
) {}
