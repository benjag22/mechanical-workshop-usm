package com.mechanical_workshop_usm.work_order_module.dto;


import com.mechanical_workshop_usm.picture_module.picture.dto.CreatePictureRequest;
import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.dto.CreateWorkOrderHasDashboardLightRequest;
import com.mechanical_workshop_usm.work_service_module.dto.CreateWorkServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


public record CreateWorkOrderFullRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "ID del record asociado")
        Integer recordId,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "IDs de servicios ya existentes")
        List<Integer> serviceIds,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Nuevos servicios a crear")
        List<CreateWorkServiceRequest> newServices,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Dashboard lights a asociar")
        List<CreateWorkOrderHasDashboardLightRequest> dashboardLightsActived
) {}