package com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity;

import com.mechanical_workshop_usm.check_in_consider_conditions_module.CheckInConsiderConditions;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mechanical_condition")
@Inheritance(strategy = InheritanceType.JOINED)
public class MechanicalCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(name = "part_name", nullable = false, length = 64)
    private String partName;

    @Column(name = "part_condition_state", length = 128)
    private String partConditionState;

    @OneToMany(mappedBy = "mechanicalCondition", cascade = CascadeType.ALL)
    private final Set<CheckInConsiderConditions> checkInRecords = new LinkedHashSet<>();

    public MechanicalCondition(String partName, String partConditionState) {
        this.partName = partName;
        this.partConditionState = partConditionState;
    }
}