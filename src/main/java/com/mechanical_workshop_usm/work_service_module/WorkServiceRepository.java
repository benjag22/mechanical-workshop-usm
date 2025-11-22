package com.mechanical_workshop_usm.work_service_module;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkServiceRepository extends JpaRepository<WorkService, Integer> {
    boolean existsByServiceName(String serviceName);
}