package com.mechanical_workshop_usm.work_order_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public record CreateWorkOrder(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Associated record ID")
        Integer recordId,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Estimated date in yyyy-MM-dd format")
        String estimatedDate,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Estimated time in HH:mm or HH:mm:ss format")
        String estimatedTime,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Path to signature file")
        String signaturePath
) {}