package com.mechanical_workshop_usm.picture_module.dashboard_light.dto;


import com.mechanical_workshop_usm.work_service_module.dto.CreateWorkServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


public record CreateDashboardLightRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "ID del record asociado")
        Integer recordId,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        List<Integer> serviceIds,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        List<CreateWorkServiceRequest> newServices,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        List<Integer> dashboardLightsActived


        ) {}