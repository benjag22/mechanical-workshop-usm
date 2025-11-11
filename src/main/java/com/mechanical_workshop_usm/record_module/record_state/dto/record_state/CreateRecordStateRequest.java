package com.mechanical_workshop_usm.record_module.record_state.dto.record_state;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateRecordStateRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String entryDate,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String entryTime,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int mileage
) {}