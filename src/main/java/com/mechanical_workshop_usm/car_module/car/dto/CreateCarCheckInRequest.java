package com.mechanical_workshop_usm.car_module.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateCarCheckInRequest(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String VIN,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String licensePlate
) {
}
