package com.mechanical_workshop_usm.work_order_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "WorkOrder Response")
public record CreateWorkOrderResponse(
        @Schema(description = "ID of the created work order")
        int id,

        @Schema(description = "Associated record ID")
        int recordId,

        @Schema(description = "Created date in yyyy-MM-dd format")
        String cratedAt,

        @Schema(description = "Estimated delivery date in yyyy-MM-dd format")
        String estimatedDelivery,

        @Schema(description = "Path to the signature file")
        String signaturePath
) {}