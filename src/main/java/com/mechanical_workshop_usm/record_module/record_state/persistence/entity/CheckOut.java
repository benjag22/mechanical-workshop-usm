package com.mechanical_workshop_usm.record_module.record_state.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "out_state")
@PrimaryKeyJoinColumn(name = "record_state_id")
public class CheckOut extends RecordState {

    @Column(name = "vehicle_diagnosis")
    private String vehicleDiagnosis;

    @Column(name = "rating")
    private byte rating;

    public CheckOut() {
        super();
    }

    public CheckOut(String vehicleDiagnosis, byte rating, Date entryDate, LocalTime entryTime, int mileage) {
        super(entryDate, entryTime, mileage);
    }
}
