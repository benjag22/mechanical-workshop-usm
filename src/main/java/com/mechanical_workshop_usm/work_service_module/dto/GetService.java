package com.mechanical_workshop_usm.work_service_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public record GetService(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "ID del servicio")
        int id,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Nombre del servicio")
        String name,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Tiempo estimado en formato HH:mm[:ss]")
        String estimatedHoursMinutes
) {}