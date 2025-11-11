package com.mechanical_workshop_usm.tool_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SingleToolResponse(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int id,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String name
) {
}