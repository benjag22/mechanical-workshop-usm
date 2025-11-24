package com.mechanical_workshop_usm.record_module.record_state.persistence.entity;

import com.mechanical_workshop_usm.check_in_consider_conditions_module.CheckInConsiderConditions;
import com.mechanical_workshop_usm.check_in_has_tools_module.CheckInHaveTool;
import com.mechanical_workshop_usm.record_module.record.Record;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "entry_state")
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @Column(name = "entry_time", nullable = false)
    private LocalDateTime entryTime;

    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Enumerated(EnumType.STRING)
    @Column(name = "gas_level", nullable = false)
    private GasLevel gasLevel;

    @Column(name = "valuables", length = 255)
    private String valuables;

    @Column(name = "observations", length = 255)
    private String observations;

    @OneToMany(mappedBy = "checkIn", cascade = CascadeType.ALL)
    private final Set<CheckInConsiderConditions> mechanicalConditions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "checkIn", cascade = CascadeType.ALL)
    private final Set<CheckInHaveTool> tools = new LinkedHashSet<>();
}
