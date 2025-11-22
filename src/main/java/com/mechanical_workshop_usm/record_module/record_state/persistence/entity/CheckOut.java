package com.mechanical_workshop_usm.record_module.record_state.persistence.entity;

import com.mechanical_workshop_usm.record_module.record.Record;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "out_state")
@PrimaryKeyJoinColumn(name = "record_state_id")
public class CheckOut extends RecordState {

    @Column(name = "vehicle_diagnosis")
    private String vehicleDiagnosis;

    public CheckOut(String vehicleDiagnosis, LocalDateTime entryTime, int mileage, Record record) {
        super(entryTime, mileage, record);
        this.vehicleDiagnosis = vehicleDiagnosis;
    }

}
