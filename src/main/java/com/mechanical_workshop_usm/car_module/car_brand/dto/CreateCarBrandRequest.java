package com.mechanical_workshop_usm.car_module.car_brand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCarBrandRequest(
        @JsonProperty("brand_name") String brandName
) {}