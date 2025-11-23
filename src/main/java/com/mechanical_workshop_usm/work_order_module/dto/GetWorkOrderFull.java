package com.mechanical_workshop_usm.work_order_module.dto;

import com.mechanical_workshop_usm.record_module.record_state.dto.check_out.CheckOutResponse;

import com.mechanical_workshop_usm.car_module.car.dto.GetCar;
import com.mechanical_workshop_usm.client_info_module.dtos.Client;
import com.mechanical_workshop_usm.image_module.image.dto.GetImage;
import com.mechanical_workshop_usm.mechanic_info_module.dto.GetMechanicInfo;
import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.dto.WorkOrderHasDashboardLightResponse;
import com.mechanical_workshop_usm.work_service_module.dto.GetService;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;



public record GetWorkOrderFull(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int id,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    boolean completed,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String createdAt,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String estimatedDelivery,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    List<GetServiceState> services,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    List<GetMechanicWorkOrder> mechanics,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String signatureUrl,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    List<GetImage> carImages,

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    List<GetWorkOrderHasDashboardLight> dashboardLights,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    Client customer,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    GetCar vehicle,

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    CheckOutResponse checkOut
) {

    public record GetMechanicWorkOrder(
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            int id,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            String name,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            String rut,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            Boolean isLeader

    ) {}

    public record GetWorkOrderHasDashboardLight(
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            int imageId,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            String url,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            String alt,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
            Boolean isFunctional

    ) {}

    public record GetServiceState(
            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Service ID")
            int id,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Service name")
            String name,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Estimated time in HH:mm[:ss] format")
            String estimatedTime,

            @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "If the service is completed")
            boolean finalized
    ) {}
}