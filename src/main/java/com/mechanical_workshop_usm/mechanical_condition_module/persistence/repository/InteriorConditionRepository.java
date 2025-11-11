package com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository;

import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.InteriorCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteriorConditionRepository extends JpaRepository<InteriorCondition, Integer> {
}
