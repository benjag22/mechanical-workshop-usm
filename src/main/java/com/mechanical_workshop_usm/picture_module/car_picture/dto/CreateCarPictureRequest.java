package com.mechanical_workshop_usm.picture_module.car_picture.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

public record CreateCarPictureRequest(
        @Schema(description = "Image file", requiredMode = Schema.RequiredMode.REQUIRED)
        MultipartFile image,

        @Schema(description = "work order id associated with the image", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer workOrderId
){}
