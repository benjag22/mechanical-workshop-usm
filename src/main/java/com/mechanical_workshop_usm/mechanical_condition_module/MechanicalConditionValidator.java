package com.mechanical_workshop_usm.mechanical_condition_module;

import com.mechanical_workshop_usm.mechanical_condition_module.dto.CreateMechanicalConditionRequest;
import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MechanicalConditionValidator {

    public void validateOnCreate(CreateMechanicalConditionRequest request) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        if (request.partName() == null || request.partName().isBlank()) {
            errors.add(new FieldErrorResponse("part_name", "Part name cannot be empty"));
        }

        if (request.partConditionState() == null || request.partConditionState().isBlank()) {
            errors.add(new FieldErrorResponse("part_condition_state", "Part condition state cannot be empty"));
        }

        if (request.conditionType() == null || request.conditionType().isBlank()) {
            errors.add(new FieldErrorResponse("condition_type", "Condition type must be specified"));
        } else if (!List.of("interior", "exterior", "electrical").contains(request.conditionType().toLowerCase())) {
            errors.add(new FieldErrorResponse("condition_type", "Condition type must be one of: interior, exterior, electrical"));
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Invalid mechanical condition fields", errors);
        }
    }
}