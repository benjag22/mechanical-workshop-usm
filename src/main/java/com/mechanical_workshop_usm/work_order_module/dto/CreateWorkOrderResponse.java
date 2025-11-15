package com.mechanical_workshop_usm.work_order_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "WorkOrder Response")
public record CreateWorkOrderResponse(
        @Schema(description = "ID of the created work order")
        Integer id,

        @Schema(description = "Associated record ID")
        Integer recordId,

        @Schema(description = "Estimated date in yyyy-MM-dd format")
        String estimatedDate,

        @Schema(description = "Estimated time in HH:mm[:ss] format")
        String estimatedTime,

        @Schema(description = "Path to the signature file")
        String signaturePath
) {}