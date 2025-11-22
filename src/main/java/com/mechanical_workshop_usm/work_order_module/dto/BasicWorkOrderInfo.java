package com.mechanical_workshop_usm.work_order_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;


public record BasicWorkOrderInfo(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int workOrderId,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int recordId,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String createdAt,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String completedAt
) {
}

