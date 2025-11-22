package com.mechanical_workshop_usm.work_order_has_mechanic_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateWorkOrderHasMechanicRequest(
        @Schema(description = "Work order id", requiredMode = Schema.RequiredMode.REQUIRED)
        int workOrderId,

        @Schema(description = "Mechanic info id", requiredMode = Schema.RequiredMode.REQUIRED)
        int mechanicInfoId,

        @Schema(description = "Whether this mechanic is the leader for the work order", requiredMode = Schema.RequiredMode.REQUIRED)
        Boolean isLeader
) {}
