package com.mechanical_workshop_usm.work_order_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record TrimmedWorkOrder(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int id,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    boolean isCompleted,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    LocalDateTime estimatedTime,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String signatureUrl,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String mechanicLeaderFullName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String clientFirstName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String clientLastName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String clientCellphoneNumber,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String carLicensePlate
) {
}
