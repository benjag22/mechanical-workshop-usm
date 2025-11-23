package com.mechanical_workshop_usm.work_order_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetWorkOrder(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int id,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    boolean isCompleted,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    boolean hasCheckOut,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String createdAt,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String deliveryTime,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String signatureUrl,
    String mechanicLeaderFullName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String clientFirstName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String clientLastName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String clientCellphoneNumber,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String carLicensePlate

) {}