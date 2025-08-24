package com.mechanical_workshop_usm.record_module.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "record_state")
@Inheritance(strategy = InheritanceType.JOINED)
public class RecordState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "entry_date")
    @Getter
    @Setter
    private Date entryDate;

    @Column(name = "entry_time")
    @Getter
    @Setter
    private LocalTime entryTime;

    @Column(name = "mileage", nullable = false)
    @Getter
    @Setter
    private int mileage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    public RecordState() {}

    public RecordState(Date entryDate, LocalTime entryTime, int mileage) {}

}
