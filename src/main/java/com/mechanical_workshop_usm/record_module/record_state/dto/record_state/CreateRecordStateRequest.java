package com.mechanical_workshop_usm.record_module.record_state.dto.record_state;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateRecordStateRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Timestamp de entrada en formato ISO local date-time, e.g. 2025-11-12T09:30:00")
        String entryTime,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Kilometraje (entero >= 0)")
        int mileage
) {}