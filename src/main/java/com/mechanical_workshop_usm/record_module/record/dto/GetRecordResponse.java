package com.mechanical_workshop_usm.record_module.record.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetRecordResponse(
        @JsonProperty("id") int id,
        @JsonProperty("reason") String reason,
        @JsonProperty("car_license_plate") String carLicensePlate,
        @JsonProperty("client_first_name") String clientFirstName
) {}
