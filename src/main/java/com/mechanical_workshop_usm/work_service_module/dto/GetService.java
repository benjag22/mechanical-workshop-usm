package com.mechanical_workshop_usm.work_service_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public record GetService(
        @Schema(description = "ID del servicio")
        Integer id,

        @Schema(description = "Nombre del servicio")
        String serviceName,

        @Schema(description = "Tiempo estimado en formato HH:mm[:ss]")
        String estimatedTime
) {}