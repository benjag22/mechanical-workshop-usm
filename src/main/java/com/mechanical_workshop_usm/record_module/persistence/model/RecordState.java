package com.mechanical_workshop_usm.record_module.persistence.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "record_state")
@Inheritance(strategy = InheritanceType.JOINED)
public class RecordState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private int id;

    @Column(name = "entry_date")
    private Date entryDate;

    @Column(name = "entry_time")
    private LocalTime entryTime;

    @Column(name = "mileage", nullable = false)
    private int mileage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    public RecordState(Date entryDate, LocalTime entryTime, int mileage) {
        this.entryDate = entryDate;
        this.entryTime = entryTime;
        this.mileage = mileage;
    }
}