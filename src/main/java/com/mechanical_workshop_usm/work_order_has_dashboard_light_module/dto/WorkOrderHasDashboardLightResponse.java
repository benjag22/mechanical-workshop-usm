package com.mechanical_workshop_usm.work_order_has_dashboard_light_module.dto;


import com.mechanical_workshop_usm.image_module.image.dto.GetImage;


public record WorkOrderHasDashboardLightResponse(
        int id,

        GetImage image,

        boolean isFunctional
) {}