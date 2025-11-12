package com.mechanical_workshop_usm.work_service_module.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;


public record CreateWorkServiceRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Nombre del servicio (máx 32 caracteres)")
        @NotBlank(message = "serviceName is required")
        String serviceName,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Tiempo estimado en formato HH:mm o HH:mm:ss")
        @NotBlank(message = "estimatedTime is required")
        @Pattern(regexp = "^\\d{1,2}:\\d{2}(:\\d{2})?$", message = "estimatedTime must be in format HH:mm or HH:mm:ss")
        String estimatedTime
) {}