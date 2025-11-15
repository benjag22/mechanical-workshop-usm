package com.mechanical_workshop_usm.mechanical_condition_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MechanicalCondition(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String partName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String state
) {
}
