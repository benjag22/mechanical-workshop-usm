package com.mechanical_workshop_usm.work_order_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public record CreateWorkOrderResponse(
        @Schema(description = "ID del work order creado")
        Integer id,

        @Schema(description = "ID del record asociado")
        Integer recordId,

        @Schema(description = "Fecha estimada en formato yyyy-MM-dd")
        String estimatedDate,

        @Schema(description = "Hora estimada en formato HH:mm[:ss]")
        String estimatedTime,

        @Schema(description = "Ruta al archivo de firma (si existe)")
        String signaturePath
) {}