package com.mechanical_workshop_usm.car_module.car_brand.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCarBrandResponse(
        int id,
        @JsonProperty("registration_number") String registrationNumber
) {}
