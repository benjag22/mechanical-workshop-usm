package com.mechanical_workshop_usm.record_module.record_state.dto.check_in;

import io.swagger.v3.oas.annotations.media.Schema;

public record MechanicalCondition(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String partName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String state
) {
}
