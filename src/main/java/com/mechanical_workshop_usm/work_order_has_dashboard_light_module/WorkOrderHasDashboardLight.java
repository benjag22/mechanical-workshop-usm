package com.mechanical_workshop_usm.work_order_has_dashboard_light_module;

import com.mechanical_workshop_usm.picture_module.dashboard_light.DashboardLight;
import com.mechanical_workshop_usm.work_order_module.WorkOrder;
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
@Table(name = "work_order_has_dashboard_light", uniqueConstraints = {@UniqueConstraint(name = "uk_wodl_pair", columnNames = {"work_order_id", "dashboard_light_id"})})
public class WorkOrderHasDashboardLight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dashboard_light_id", nullable = false)
    private DashboardLight dashboardLight;

    @Column(name = "present", nullable = false)
    private boolean present = false;

    @Column(name = "operates", nullable = false)
    private boolean operates = false;

    public WorkOrderHasDashboardLight(WorkOrder workOrder, DashboardLight dashboardLight, boolean present, boolean operates) {
        this.workOrder = workOrder;
        this.dashboardLight = dashboardLight;
        this.present = present;
        this.operates = operates;
    }
}