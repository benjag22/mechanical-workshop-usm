package com.mechanical_workshop_usm.work_order_realized_service_module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface WorkOrderRealizedServiceRepository extends JpaRepository<WorkOrderRealizedService, Integer> {
    List<WorkOrderRealizedService> findByWorkOrder_Id(Integer workOrderId);
    List<WorkOrderRealizedService> findByWorkService_Id(Integer workServiceId);
    boolean existsByWorkOrder_IdAndWorkService_Id(Integer workOrderId, Integer workServiceId);

    Optional<WorkOrderRealizedService> findByWorkOrder_IdAndWorkService_Id(Integer workOrderId, Integer workServiceId);}