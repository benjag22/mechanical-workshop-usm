package com.mechanical_workshop_usm.work_order_has_mechanic_module.dto;

public record GetWorkOrderHasMechanicResponse(
        Integer id,
        Integer workOrderId,
        Integer mechanicInfoId,
        boolean isLeader
) {}
