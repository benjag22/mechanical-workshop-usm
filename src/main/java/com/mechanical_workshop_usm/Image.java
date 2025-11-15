package com.mechanical_workshop_usm;

import io.swagger.v3.oas.annotations.media.Schema;

public record Image(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    int id,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String url,

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String alt
) {
}
