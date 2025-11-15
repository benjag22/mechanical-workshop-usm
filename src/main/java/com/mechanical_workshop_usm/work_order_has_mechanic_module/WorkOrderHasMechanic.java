package com.mechanical_workshop_usm.work_order_has_mechanic_module;

import com.mechanical_workshop_usm.work_order_module.WorkOrder;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfo;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "work_order_has_mechanics", uniqueConstraints = @UniqueConstraint(name = "uk_womh_pair", columnNames = {"work_order_id", "mechanic_info_id"})
)
public class WorkOrderHasMechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_womh_work_order"))
    private WorkOrder workOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mechanic_info_id", nullable = false, foreignKey = @ForeignKey(name = "fk_womh_mechanic_info"))
    private MechanicInfo mechanicInfo;

    @Column(name = "is_leader", nullable = false)
    private boolean isLeader;

    public WorkOrderHasMechanic(WorkOrder workOrder, MechanicInfo mechanicInfo, boolean isLeader) {
        this.workOrder = workOrder;
        this.mechanicInfo = mechanicInfo;
        this.isLeader = isLeader;
    }
}