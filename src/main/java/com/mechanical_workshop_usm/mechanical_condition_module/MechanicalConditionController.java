package com.mechanical_workshop_usm.mechanical_condition_module;

import com.mechanical_workshop_usm.mechanical_condition_module.dto.CreateMechanicalConditionRequest;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.CreateMechanicalConditionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mechanical-condition")
public class MechanicalConditionController {
    private final MechanicalConditionService service;

    public MechanicalConditionController(MechanicalConditionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CreateMechanicalConditionResponse> createMechanicalCondition(
            @RequestBody CreateMechanicalConditionRequest request
    ) {
        CreateMechanicalConditionResponse response = service.createMechanicalCondition(request);
        return ResponseEntity.ok(response);
    }
}