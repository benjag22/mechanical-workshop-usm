package com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "electrical_system_condition")
@PrimaryKeyJoinColumn(name = "mechanical_condition_id")
public class ElectricalSystemCondition extends MechanicalCondition {
    public ElectricalSystemCondition() {
        super();
    }
    public ElectricalSystemCondition(String partName, String partConditionState) {
        super(partName, partConditionState);
    }
}
