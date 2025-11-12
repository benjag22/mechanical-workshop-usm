package com.mechanical_workshop_usm.work_order_module;

import org.springframework.data.jpa.repository.JpaRepository;
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
}