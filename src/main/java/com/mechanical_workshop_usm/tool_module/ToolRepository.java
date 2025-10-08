package com.mechanical_workshop_usm.tool_module;

import com.mechanical_workshop_usm.car_module.car_model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Integer> {
    Optional<CarModel> findByToolName(String toolName);

}