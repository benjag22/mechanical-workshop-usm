package com.mechanical_workshop_usm.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalle de un error específico por campo")
public record FieldErrorResponse(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Campo afectado", example = "clientId")
        String field,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Descripción del error", example = "Debe ser mayor a 0")
        String message
) {}