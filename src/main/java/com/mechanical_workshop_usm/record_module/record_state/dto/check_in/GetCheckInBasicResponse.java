package com.mechanical_workshop_usm.record_module.record_state.dto.check_in;

import com.mechanical_workshop_usm.mechanical_condition_module.dto.MechanicalCondition;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GetCheckInBasicResponse(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    Integer checkInId,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String clientName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String clientEmail,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String brandName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String modelName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String modelType,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    Integer modelYear,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String licensePlate,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String reason,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String observations,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    List<MechanicalCondition> conditions,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String entryTime
) {
}