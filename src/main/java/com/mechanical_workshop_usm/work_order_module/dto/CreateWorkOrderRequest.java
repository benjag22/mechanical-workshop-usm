package com.mechanical_workshop_usm.work_order_module.dto;

import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicRequest;
import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.dto.CreateWorkOrderHasDashboardLightRequest;
import com.mechanical_workshop_usm.work_service_module.dto.CreateWorkServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CreateWorkOrderRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Associated record ID")
        Integer recordId,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Existing service IDs")
        List<Integer> serviceIds,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "New services to be created")
        List<CreateWorkServiceRequest> newServices,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Dashboard lights to associate")
        List<CreateWorkOrderHasDashboardLightRequest> dashboardLightsActive,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Mechanic IDs to associate with the order")
        List<Integer> mechanicIds,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "New mechanics to be created")
        List<CreateMechanicRequest> newMechanics,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "ID of the mechanic who will be the leader")
        Integer leaderMechanicId,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "New mechanics to be created")
        CreateMechanicRequest newLeaderMechanic
) {}