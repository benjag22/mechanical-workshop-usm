package com.mechanical_workshop_usm.picture_module.picture.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Picture response")
public record GetPictureResponse(
        @Schema(
                description = "Numeric identifier",
                example = "123",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        Integer id,

        @Schema(
                description = "Alternative text for the image",
                example = "Front view of the engine"
        )
        String alt,

        @Schema(
                description = "Public URL to access the image",
                example = "https://cdn.example.com/pictures/123.jpg",
                type = "string",
                format = "uri",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        String url
) {}