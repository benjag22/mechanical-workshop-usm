package com.mechanical_workshop_usm.record_module.persistence.model;

import com.mechanical_workshop_usm.car_module.persistence.model.Car;
import com.mechanical_workshop_usm.client_info_module.ClientInfo;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfoModel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "record")
@Builder
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "reason", nullable = false)
    @Getter
    @Setter
    private String reason;

    @OneToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    @JoinColumn(name = "client_info_id")
    private ClientInfo clientInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    @JoinColumn(name = "mechanic_info_id")
    private MechanicInfoModel mechanicInfo;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecordState> recordStates = new LinkedHashSet<>();

    public Record() {}

    public Record(String reason) {}
}
