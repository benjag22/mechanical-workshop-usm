package com.mechanical_workshop_usm.work_order_module.projections;

import java.time.LocalDateTime;

public interface TrimmedWorkOrderInfoProjection {

    int getWorkOrderId();
    boolean isCompleted();
    LocalDateTime getEstimatedTime();
    String getSignaturePath();
    String getMechanicLeaderFullName();
    String getClientFirstName();
    String getClientLastName();
    String getClientCellphoneNumber();
    String getCarLicensePlate();
}
