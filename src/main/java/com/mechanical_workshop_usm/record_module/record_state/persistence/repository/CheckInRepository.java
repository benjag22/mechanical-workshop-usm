package com.mechanical_workshop_usm.record_module.record_state.persistence.repository;

import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckIn;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {

    @NotNull
    @Override
    @EntityGraph(attributePaths = {
            "record",
            "record.clientInfo",
            "record.car",
            "record.car.carModel",
            "record.car.carModel.brand",
            "mechanicalConditions.exteriorCondition",
            "mechanicalConditions.interiorCondition",
            "mechanicalConditions.electricalSystemCondition",
            "tools.tool"
    })
    List<CheckIn> findAll();

    @Query("""
  SELECT ci FROM CheckIn ci
  WHERE NOT EXISTS (
    SELECT wo FROM WorkOrder wo
    WHERE wo.record.id = ci.record.id
  )
""")
    List<CheckIn> findCheckInsNotLinkedToWorkOrder();
}