package com.mechanical_workshop_usm.mechanical_condition_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GroupedMechanicalCondition(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String partName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    List<SingleMechanicalCondition> conditions
) {

    public record SingleMechanicalCondition(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int id,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String conditionState
    ) {
    }
}

