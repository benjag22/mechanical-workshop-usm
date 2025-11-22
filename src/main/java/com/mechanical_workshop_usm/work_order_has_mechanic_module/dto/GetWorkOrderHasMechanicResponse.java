package com.mechanical_workshop_usm.work_order_has_mechanic_module.dto;

public record GetWorkOrderHasMechanicResponse(
        int id,
        int workOrderId,
        int mechanicInfoId,
        boolean isLeader
) {}
