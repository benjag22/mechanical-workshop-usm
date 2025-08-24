package com.mechanical_workshop_usm.work_order_realized_service_module.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "work_order")
public class WorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "estimated_date", nullable = false)
    @Getter
    @Setter
    private Date estimatedDate;

    @Column(name = "estimated_time", nullable = false)
    @Getter
    @Setter
    private LocalTime estimatedTime;

    public WorkOrder() {}

    public WorkOrder(Date estimatedDate, LocalTime estimatedTime) {}
}
