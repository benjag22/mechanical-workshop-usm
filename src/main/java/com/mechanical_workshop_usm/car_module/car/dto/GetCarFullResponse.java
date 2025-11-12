package com.mechanical_workshop_usm.car_module.car.dto;
import io.swagger.v3.oas.annotations.media.Schema;

public record GetCarFullResponse(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int id,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String VIN,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String licensePlate,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int modelId,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String modelName,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String modelType,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int modelYear,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int brandId,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String brandName
) {
}
