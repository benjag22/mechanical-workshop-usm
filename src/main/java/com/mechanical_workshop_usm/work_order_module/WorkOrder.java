package com.mechanical_workshop_usm.work_order_module;

import com.mechanical_workshop_usm.record_module.record.Record;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_order")
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;


    @Column(name = "completed", nullable = false)
    private boolean completed;

    @Column(name = "estimated_time", nullable = false)
    private LocalTime estimatedTime;

    @Column(name = "signature_path", length = 512)
    private String signaturePath;

    public WorkOrder(Record record, LocalTime estimatedTime, String signaturePath) {
        this.record = record;
        this.estimatedTime = estimatedTime;
        this.signaturePath = signaturePath;
    }
}