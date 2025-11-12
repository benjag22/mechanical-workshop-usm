package com.mechanical_workshop_usm.work_service_module;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "work_service", uniqueConstraints = {@UniqueConstraint(name = "uk_work_service_service_name", columnNames = {"service_name"})})
@NoArgsConstructor
public class WorkService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "service_name", length = 32, nullable = false)
    private String serviceName;

    @Column(name = "estimated_time", nullable = false)
    private LocalTime estimatedTime;

    public WorkService(String serviceName, LocalTime estimatedTime) {
        this.serviceName = serviceName;
        this.estimatedTime = estimatedTime;
    }
}