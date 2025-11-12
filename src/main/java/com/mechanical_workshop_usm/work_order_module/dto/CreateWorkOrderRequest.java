package com.mechanical_workshop_usm.work_order_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public record CreateWorkOrderRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "ID del record asociado")
        Integer recordId,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Fecha estimada en formato yyyy-MM-dd")
        String estimatedDate,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Hora estimada en formato HH:mm o HH:mm:ss")
        String estimatedTime,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Ruta al archivo de firma")
        String signaturePath
) {}