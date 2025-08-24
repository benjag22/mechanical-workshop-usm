package com.mechanical_workshop_usm.work_order_realized_service_module.persistence.model;

import java.io.Serializable;

public record WorkOrderRealizedServicesPk(int workOrder, int workService) implements Serializable {
}
