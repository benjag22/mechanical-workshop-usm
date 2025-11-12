package com.mechanical_workshop_usm.car_module.car_brand.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public record CreateCarBrandRequest(
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String brandName
) {}