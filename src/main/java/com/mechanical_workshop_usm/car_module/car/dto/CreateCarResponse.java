package com.mechanical_workshop_usm.car_module.car.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCarResponse(
        int id,
        @JsonProperty("license_plate") String licensePlate
) {}