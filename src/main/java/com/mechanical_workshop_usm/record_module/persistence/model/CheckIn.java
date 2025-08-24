package com.mechanical_workshop_usm.record_module.persistence.model;

import com.mechanical_workshop_usm.check_in_consider_conditions_module.CheckInConsiderConditions;
import com.mechanical_workshop_usm.check_in_has_tools_module.persistence.model.CheckInHaveTool;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "entry_state")
@PrimaryKeyJoinColumn(name = "record_state_id")
public class CheckIn extends RecordState {

    @Column(name = "gas_level", nullable = false)
    private GasLevel gasLevel;

    @Column(name = "valuables", length = 255)
    private String valuables;

    @OneToMany(mappedBy = "checkIn", cascade = CascadeType.ALL)
    private final Set<CheckInConsiderConditions> mechanicalConditions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "checkIn", cascade = CascadeType.ALL)
    private final Set<CheckInHaveTool> tools = new LinkedHashSet<>();

    public CheckIn() {
        super();
    }

    public CheckIn(GasLevel gasLevel, String valuables, Date entryDate, LocalTime entryTime, int mileage) {
        super(entryDate, entryTime, mileage);
    }
}
