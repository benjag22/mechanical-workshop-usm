package com.mechanical_workshop_usm.car_module.car_model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateCarModelCheckInRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String modelName,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String modelType,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        int modelYear
) {
}
