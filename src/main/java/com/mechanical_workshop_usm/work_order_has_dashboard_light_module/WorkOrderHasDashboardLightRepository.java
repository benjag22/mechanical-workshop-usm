package com.mechanical_workshop_usm.work_order_has_dashboard_light_module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOrderHasDashboardLightRepository extends JpaRepository<WorkOrderHasDashboardLight, Integer> {
    List<WorkOrderHasDashboardLight> findByWorkOrder_Id(Integer workOrderId);
    List<WorkOrderHasDashboardLight> findByDashboardLight_Id(Integer dashboardLightId);
    boolean existsByWorkOrder_IdAndDashboardLight_Id(Integer workOrderId, Integer dashboardLightId);
}