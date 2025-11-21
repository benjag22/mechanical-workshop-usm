package com.mechanical_workshop_usm.client_info_module.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateClientRequest(
        String firstName,

        String rut,

        String lastName,

        String emailAddress,

        String address,

        String cellphoneNumber
) {
}
