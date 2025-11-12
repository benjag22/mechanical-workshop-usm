package com.mechanical_workshop_usm.car_module.car.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateCarRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String VIN,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String licensePlate,

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    int modelId
) {
}

