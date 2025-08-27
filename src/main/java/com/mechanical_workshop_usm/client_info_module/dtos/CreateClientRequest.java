package com.mechanical_workshop_usm.client_info_module.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateClientRequest(
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("email_address") String emailAddress,
        @JsonProperty("address") String address,
        @JsonProperty("cellphone_number") String cellphoneNumber
) {
}
