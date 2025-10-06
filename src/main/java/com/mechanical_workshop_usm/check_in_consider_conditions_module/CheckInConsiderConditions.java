package com.mechanical_workshop_usm.check_in_consider_conditions_module;

import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.MechanicalCondition;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckIn;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "entry_state_consider_condition")
@EqualsAndHashCode(exclude = {"mechanicalCondition", "checkIn"})
@IdClass(CheckInConsiderConditionsPK.class)
public class CheckInConsiderConditions {

    @Id
    @ManyToOne
    @JoinColumn(name = "mechanical_condition_id")
    private MechanicalCondition mechanicalCondition;

    @Id
    @ManyToOne
    @JoinColumn(name = "entry_state_id")
    private CheckIn checkIn;
}
