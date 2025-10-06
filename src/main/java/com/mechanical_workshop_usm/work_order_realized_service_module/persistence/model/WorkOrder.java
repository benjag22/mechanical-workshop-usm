package com.mechanical_workshop_usm.work_order_realized_service_module.persistence.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.time.LocalDate;

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
    private int id;

    @Column(name = "estimated_date", nullable = false)
    private LocalDate estimatedDate;

    @Column(name = "estimated_time", nullable = false)
    private LocalTime estimatedTime;

    public WorkOrder(LocalDate estimatedDate, LocalTime estimatedTime) {
        this.estimatedDate = estimatedDate;
        this.estimatedTime = estimatedTime;
    }
}
