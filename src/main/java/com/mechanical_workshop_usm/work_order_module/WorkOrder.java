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
import java.time.LocalDateTime;
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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "estimated_delivery", nullable = false)
    private LocalDateTime estimatedDelivery;

    @Column(name = "signature_path", length = 512)
    private String signaturePath;

    public WorkOrder(Record record,
                     LocalDateTime createdAt,
                     LocalDateTime estimatedDelivery,
                     String signaturePath
    ) {
        this.record = record;
        this.createdAt = createdAt;
        this.estimatedDelivery = estimatedDelivery;
        this.signaturePath = signaturePath;
    }
}