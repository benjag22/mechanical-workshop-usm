package com.mechanical_workshop_usm.work_service_module;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

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

    @Column(name = "estimated_time_minutes", nullable = false)
    private Long estimatedTimeMinutes;

    @Transient
    public Duration getEstimatedTime() {
        return Duration.ofMinutes(estimatedTimeMinutes);
    }

    public WorkService(String serviceName, Duration estimatedTime) {
        this.serviceName = serviceName;
        this.estimatedTimeMinutes = estimatedTime.toMinutes();
    }
}
