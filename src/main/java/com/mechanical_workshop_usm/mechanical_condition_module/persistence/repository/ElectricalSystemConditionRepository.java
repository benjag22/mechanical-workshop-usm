package com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository;

import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ElectricalSystemCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricalSystemConditionRepository extends JpaRepository<ElectricalSystemCondition, Integer> {
}
