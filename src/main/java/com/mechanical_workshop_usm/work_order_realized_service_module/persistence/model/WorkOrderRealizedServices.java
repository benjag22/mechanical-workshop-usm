package com.mechanical_workshop_usm.work_order_realized_service_module.persistence.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "work_order_realized_service")
@EqualsAndHashCode(exclude = {"workOrder", "workService"})
@IdClass(WorkOrderRealizedServicesPk.class)
public class WorkOrderRealizedServices {

    @Id
    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

    @Id
    @ManyToOne
    @JoinColumn(name = "work_service_id")
    private WorkService workService;

    @Column(name = "finalized")
    private boolean finalized = false;

}
