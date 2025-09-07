package com.mechanical_workshop_usm.mechanic_info_module.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateMechanicResponse(
        long id,
        @JsonProperty("registration_number") String registrationNumber
) {}