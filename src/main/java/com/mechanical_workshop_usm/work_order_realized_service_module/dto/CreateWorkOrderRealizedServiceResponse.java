package com.mechanical_workshop_usm.work_order_realized_service_module.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public record CreateWorkOrderRealizedServiceResponse(
        @Schema(description = "ID del registro")
        Integer id,

        @Schema(description = "ID del work order asociado")
        Integer workOrderId,

        @Schema(description = "ID del work service asociado")
        Integer workServiceId,

        @Schema(description = "Si el servicio está finalizado")
        boolean finalized
) {}