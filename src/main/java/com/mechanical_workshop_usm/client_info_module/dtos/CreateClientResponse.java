package com.mechanical_workshop_usm.client_info_module.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateClientResponse(
        long id,
        @JsonProperty("cellphone_name") String cellphoneNumber
) {
}
