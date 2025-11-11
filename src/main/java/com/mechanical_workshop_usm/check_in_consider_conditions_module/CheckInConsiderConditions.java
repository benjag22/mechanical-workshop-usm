package com.mechanical_workshop_usm.check_in_consider_conditions_module;

import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ElectricalSystemCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ExteriorCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.InteriorCondition;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckIn;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "entry_state_consider_condition")
@EqualsAndHashCode(exclude = {"exteriorCondition", "interiorCondition", "electricalSystemCondition",  "checkIn"})

public class CheckInConsiderConditions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "entry_state_id")
    private CheckIn checkIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exterior_condition_id")
    private ExteriorCondition exteriorCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interior_condition_id")
    private InteriorCondition interiorCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electrical_system_condition_id")
    private ElectricalSystemCondition electricalSystemCondition;

}
