package com.mechanical_workshop_usm.work_order_module.dto;

import com.mechanical_workshop_usm.car_module.car.dto.GetCar;
import com.mechanical_workshop_usm.client_info_module.dtos.Client;
import com.mechanical_workshop_usm.image_module.image.dto.CreateImageRequest;
import com.mechanical_workshop_usm.mechanic_info_module.dto.GetMechanicInfo;
import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.dto.WorkOrderHasDashboardLightResponse;
import com.mechanical_workshop_usm.work_service_module.dto.GetService;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


public record GetWorkOrderFull(
        Integer id,
        String createdAt,
        String estimatedDelivery,

        List<GetService> services,

        String signatureUrl,
        List<CreateImageRequest> carImages,
        List<DashboardLightInfo> dashboardLights,

        List<GetMechanicInfo> mechanics,

        Client customer,

        GetCar vehicle
) {
    public record DashboardLightInfo(
            Integer id,
            Integer num,
            String name
    ) {}

    public record GetMechanicWorkOrder(
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            int id,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            String name,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            String rut,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            Boolean isLeader

    ) {
    }

}