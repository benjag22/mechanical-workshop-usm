package com.mechanical_workshop_usm.record_module.record_state.persistence.entity;

import com.mechanical_workshop_usm.record_module.record.Record;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "out_state")
public class CheckOut {

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

    @Column(name = "vehicle_diagnosis")
    private String vehicleDiagnosis;
}
