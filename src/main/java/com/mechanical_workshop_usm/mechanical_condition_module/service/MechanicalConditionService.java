package com.mechanical_workshop_usm.mechanical_condition_module.service;

import com.mechanical_workshop_usm.mechanical_condition_module.dto.CreateMechanicalConditionRequest;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.MechanicalConditionResponse;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.MechanicalCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.InteriorCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ExteriorCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ElectricalSystemCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository.MechanicalConditionRepository;
import org.springframework.stereotype.Service;

@Service
public class MechanicalConditionService {
    private final MechanicalConditionRepository repository;
    private final MechanicalConditionValidator validator;

    public MechanicalConditionService(MechanicalConditionRepository repository, MechanicalConditionValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public MechanicalConditionResponse createMechanicalCondition(CreateMechanicalConditionRequest request) {
        validator.validateOnCreate(request);

        MechanicalCondition condition;
        switch (request.conditionType().toLowerCase()) {
            case "interior" -> condition = new InteriorCondition(request.partName(), request.partConditionState());
            case "exterior" -> condition = new ExteriorCondition(request.partName(), request.partConditionState());
            case "electrical" -> condition = new ElectricalSystemCondition(request.partName(), request.partConditionState());
            default -> throw new IllegalArgumentException("Invalid condition type");
        }

        MechanicalCondition saved = repository.save(condition);

        return new MechanicalConditionResponse(
                saved.getId(),
                saved.getPartName(),
                saved.getPartConditionState(),
                request.conditionType().toLowerCase()
        );
    }

}