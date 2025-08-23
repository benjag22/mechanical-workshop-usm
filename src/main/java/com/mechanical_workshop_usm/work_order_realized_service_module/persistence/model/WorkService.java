package com.mechanical_workshop_usm.work_order_realized_service_module.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "work_service")
public class WorkService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "service_name", unique = true, nullable = false)
    @Getter
    @Setter
    private String serviceName;

    public WorkService() {}

    public WorkService(String serviceName) {}
}
