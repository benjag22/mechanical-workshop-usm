package com.mechanical_workshop_usm.work_order_has_mechanic_module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOrderHasMechanicRepository extends JpaRepository<WorkOrderHasMechanic, Integer> {

    List<WorkOrderHasMechanic> findByWorkOrder_Id(Integer workOrderId);

    List<WorkOrderHasMechanic> findByMechanicInfo_Id(Integer mechanicInfoId);

    boolean existsByWorkOrder_IdAndMechanicInfo_Id(Integer workOrderId, Integer mechanicInfoId);
}