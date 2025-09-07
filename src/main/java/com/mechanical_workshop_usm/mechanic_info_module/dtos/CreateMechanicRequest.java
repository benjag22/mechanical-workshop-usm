package com.mechanical_workshop_usm.mechanic_info_module.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateMechanicRequest(
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("registration_number") String registrationNumber
) {}