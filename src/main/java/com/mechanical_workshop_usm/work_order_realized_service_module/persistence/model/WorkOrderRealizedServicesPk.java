package com.mechanical_workshop_usm.work_order_realized_service_module.persistence.model;

import java.io.Serializable;

public record WorkOrderRealizedServicesPk(long workOrder, long workService) implements Serializable {
}
