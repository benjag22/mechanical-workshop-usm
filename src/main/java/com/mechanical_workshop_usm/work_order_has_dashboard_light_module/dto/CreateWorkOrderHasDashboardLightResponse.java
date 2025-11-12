package com.mechanical_workshop_usm.work_order_has_dashboard_light_module.dto;


import io.swagger.v3.oas.annotations.media.Schema;


public record CreateWorkOrderHasDashboardLightResponse(
        @Schema(description = "ID del registro")
        Integer id,

        @Schema(description = "ID del work order")
        Integer workOrderId,

        @Schema(description = "ID del dashboard light")
        Integer dashboardLightId,

        @Schema(description = "Si está presente")
        boolean present,

        @Schema(description = "Si opera correctamente")
        boolean operates
) {}