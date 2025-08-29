package com.mechanical_workshop_usm.record_module.persistence.model;

import com.mechanical_workshop_usm.car_module.persistence.entity.Car;
import com.mechanical_workshop_usm.client_info_module.ClientInfo;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private int id;

    @Column(name = "reason", nullable = false)
    private String reason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_info_id")
    private ClientInfo clientInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanic_info_id")
    private MechanicInfo mechanicInfo;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecordState> recordStates = new LinkedHashSet<>();

    public Record(String reason) {
        this.reason = reason;
    }
}