package com.mechanical_workshop_usm.dashboard_light_module.dto;

import java.time.Instant;

public record CreateDashboardLightResponse(
        Integer id,
        String alt,
        String path
) {}