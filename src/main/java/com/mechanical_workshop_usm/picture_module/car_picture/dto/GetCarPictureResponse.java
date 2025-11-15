package com.mechanical_workshop_usm.picture_module.car_picture.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetCarPictureResponse(
        @Schema(
                description = "Numeric identifier",
                example = "123",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        Integer id,

        @Schema(
                description = "Id de orden de trabajo asosaida a la imagen",
                example = "Front view of the engine"
        )
        Integer workOrderId,

        @Schema(
                description = "Public URL to access the image",
                example = "https://cdn.example.com/pictures/123.jpg",
                type = "string",
                format = "uri",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        String url
){}
