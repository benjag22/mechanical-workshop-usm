package com.mechanical_workshop_usm.mechanical_condition.persistence.repository;

import com.mechanical_workshop_usm.mechanical_condition.persistence.model.MechanicalCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MechanicalConditionRepository extends JpaRepository<MechanicalCondition, Integer> {
}
