package com.mechanical_workshop_usm.mechanical_condition_module.controllers;

import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.InteriorCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.MechanicalCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository.MechanicalConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mechanical-condition")
public class MechanicalConditionController {
    private final MechanicalConditionRepository mechanicalConditionRepository;
    @Autowired
    public MechanicalConditionController(MechanicalConditionRepository mechanicalConditionRepository) {
        this.mechanicalConditionRepository = mechanicalConditionRepository;
    }

    @PostMapping("/test")
    public MechanicalCondition test() {
        InteriorCondition interior = new InteriorCondition("asiento", "mal");
        return mechanicalConditionRepository.save(interior);
    }
}
