package com.mechanical_workshop_usm.client_info_module.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateClientRequest(

        @Schema(requiredMode =  Schema.RequiredMode.REQUIRED)
        String firstName,

        @Schema(requiredMode =  Schema.RequiredMode.REQUIRED)
        String rut,

        @Schema(requiredMode =  Schema.RequiredMode.NOT_REQUIRED)
        String lastName,

        @Schema(requiredMode =  Schema.RequiredMode.REQUIRED)
        String emailAddress,

        @Schema(requiredMode =  Schema.RequiredMode.REQUIRED)
        String address,

        @Schema(requiredMode =  Schema.RequiredMode.REQUIRED)
        String cellphoneNumber
) {
}
