package com.mechanical_workshop_usm.work_order_realized_service_module;


import com.mechanical_workshop_usm.work_order_module.WorkOrder;
import com.mechanical_workshop_usm.work_service_module.WorkService;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_order_realized_service")
public class WorkOrderRealizedService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_service_id", nullable = false)
    private WorkService workService;

    @Column(name = "finalized", nullable = false)
    private boolean finalized;

    public WorkOrderRealizedService(WorkOrder workOrder, WorkService workService, boolean finalized) {
        this.workOrder = workOrder;
        this.workService = workService;
        this.finalized = finalized;
    }
}
