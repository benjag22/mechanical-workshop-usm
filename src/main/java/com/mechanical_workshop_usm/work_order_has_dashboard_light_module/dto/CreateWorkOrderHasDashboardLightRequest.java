package com.mechanical_workshop_usm.work_order_has_dashboard_light_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateWorkOrderHasDashboardLightRequest(

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "ID del dashboard light")
        int dashboardLightId,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Si el dashboard light está presente (default false)")
        boolean present,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Si el dashboard light opera correctamente (default false)")
        boolean isFunctional
) {}