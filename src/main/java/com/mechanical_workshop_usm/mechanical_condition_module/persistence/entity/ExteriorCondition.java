package com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "exterior_condition")
@PrimaryKeyJoinColumn(name = "mechanical_condition_id")
public class ExteriorCondition extends MechanicalCondition {
    public ExteriorCondition() {
        super();
    }
    public ExteriorCondition(String partName, String partConditionState) {
        super(partName, partConditionState);
    }
}
