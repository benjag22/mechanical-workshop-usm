package com.mechanical_workshop_usm.work_order_module;

import com.mechanical_workshop_usm.work_order_module.projections.TrimmedWorkOrderInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Integer> {

    List<WorkOrder> findByRecord_Id(Integer recordId);

    List<WorkOrder> findByEstimatedDateBetween(LocalDate start, LocalDate end);

    boolean existsByRecord_Id(Integer recordId);

    @Query("select w from WorkOrder w join fetch w.record r where w.id = :id")
    Optional<WorkOrder> findByIdWithRecord(@Param("id") Integer id);

    @Query("select w from WorkOrder w where w.record.id = :recordId order by w.estimatedDate desc, w.estimatedTime desc")
    List<WorkOrder> findLatestForRecord(@Param("recordId") Integer recordId);

    @NativeQuery(
        """
        select
            wo.id as work_order_id,
            wo.completed,
            wo.estimated_time,
            wo.signature_path,
            leader_mechanic.name as mechanic_leader_name,
            ci.first_name as client_name,
            ci.last_name as client_last_name,
            ci.cellphone_number as client_phone,
            c.license_plate as car_license_plate
        
        from work_order wo
                 inner join record r on wo.record_id = r.id
                 inner join client_info ci on r.client_info_id = ci.id
                 inner join car c on r.car_id = c.id
                 left join work_order_has_mechanics wohm on wo.id = wohm.work_order_id and wohm.is_leader = true
                 left join mechanic_info leader_mechanic on wohm.mechanic_info_id = leader_mechanic.id
        
        order by wo.id desc
        """
    )
    List<TrimmedWorkOrderInfoProjection> findTrimmedWorkOrders();
}