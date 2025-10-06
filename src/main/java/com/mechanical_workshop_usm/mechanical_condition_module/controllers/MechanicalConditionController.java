package com.mechanical_workshop_usm.mechanical_condition_module.controllers;

import com.mechanical_workshop_usm.mechanical_condition_module.dto.CreateMechanicalConditionRequest;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.MechanicalConditionResponse;
import com.mechanical_workshop_usm.mechanical_condition_module.service.MechanicalConditionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mechanical-condition")
public class MechanicalConditionController {
    private final MechanicalConditionService service;

    public MechanicalConditionController(MechanicalConditionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MechanicalConditionResponse> createMechanicalCondition(
            @RequestBody CreateMechanicalConditionRequest request
    ) {
        MechanicalConditionResponse response = service.createMechanicalCondition(request);
        return ResponseEntity.ok(response);
    }
}