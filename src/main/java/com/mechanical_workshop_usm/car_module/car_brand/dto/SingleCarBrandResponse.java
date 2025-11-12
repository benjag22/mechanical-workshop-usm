package com.mechanical_workshop_usm.car_module.car_brand.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public record SingleCarBrandResponse(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        int id,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String brandName
){}
