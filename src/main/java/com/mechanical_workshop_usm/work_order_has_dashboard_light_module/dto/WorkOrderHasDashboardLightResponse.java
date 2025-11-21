package com.mechanical_workshop_usm.work_order_has_dashboard_light_module.dto;


import com.mechanical_workshop_usm.image_module.image.Image;
import com.mechanical_workshop_usm.image_module.image.dto.CreateImageRequest;
import io.swagger.v3.oas.annotations.media.Schema;


public record WorkOrderHasDashboardLightResponse(
        Integer id,

        Integer workOrderId,

        CreateImageRequest image,

        boolean isFunctional
) {}