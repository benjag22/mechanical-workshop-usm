package com.mechanical_workshop_usm.picture_module.dashboard_light.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

public record CreateDashboardLightRequest(
        @Schema(description = "Image file", requiredMode = Schema.RequiredMode.REQUIRED)
        MultipartFile image,
        @Schema(description = "Alternative text for the image", requiredMode = Schema.RequiredMode.REQUIRED)
        String alt
) {}