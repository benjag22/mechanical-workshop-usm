package com.mechanical_workshop_usm.work_order_realized_service_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateWorkOrderRealizedServiceRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "ID del work order asociado")
        int workOrderId,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "ID del work service")
        int workServiceId,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Si el servicio está finalizado")
        boolean finalized
) {}