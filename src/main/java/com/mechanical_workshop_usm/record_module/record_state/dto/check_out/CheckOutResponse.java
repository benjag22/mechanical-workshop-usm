package com.mechanical_workshop_usm.record_module.record_state.dto.check_out;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CheckOutResponse(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int id,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    LocalDateTime entryTime,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int mileage,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String vehicleDiagnosis
) {}