package com.mechanical_workshop_usm.image_module.image.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetImage(

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String url,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String alt
) {
}
