package com.mechanical_workshop_usm.work_order_module.dto;

import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.dto.CreateWorkOrderHasDashboardLightRequest;
import com.mechanical_workshop_usm.work_service_module.dto.CreateWorkServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreateWorkOrderRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "ID del record asociado")
        Integer recordId,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "IDs de servicios ya existentes")
        List<Integer> serviceIds,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Nuevos servicios a crear")
        List<CreateWorkServiceRequest> newServices,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Dashboard lights a asociar")
        List<CreateWorkOrderHasDashboardLightRequest> dashboardLightsActive,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "IDs de mecánicos a asociar a la orden (solo IDs).")
        List<Integer> mechanicIds,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "ID del mecánico que será el líder (si aplica)")
        Integer leaderMechanicId,

        @Schema(description = "Signature image file", requiredMode = Schema.RequiredMode.REQUIRED)
        List<MultipartFile> carPictures,

        @Schema(description = "Signature image file", requiredMode = Schema.RequiredMode.REQUIRED)
        MultipartFile signature
) {}