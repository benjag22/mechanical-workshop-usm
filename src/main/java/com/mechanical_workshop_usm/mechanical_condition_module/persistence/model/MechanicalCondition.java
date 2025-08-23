package com.mechanical_workshop_usm.mechanical_condition_module.persistence.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mechanical_condition")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class MechanicalCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "part_name", nullable = false, length = 64)
    private String partName;

    @Column(name = "part_condition_state", length = 128)
    private String partConditionState;

    public MechanicalCondition() {}

    public MechanicalCondition(String partName, String partConditionState) {
        this.partName = partName;
        this.partConditionState = partConditionState;
    }
}