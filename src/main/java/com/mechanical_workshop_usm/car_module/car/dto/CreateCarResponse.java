package com.mechanical_workshop_usm.car_module.car.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCarResponse(
        long id,
        @JsonProperty("vin") String VIN,
        @JsonProperty("license_plate") String licensePlate
) {}