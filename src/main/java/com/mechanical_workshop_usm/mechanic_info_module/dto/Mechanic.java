package com.mechanical_workshop_usm.mechanic_info_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record Mechanic(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int id,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String name,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String rut
) {
}
