package com.mechanical_workshop_usm.mechanical_condition_module;

import com.mechanical_workshop_usm.mechanical_condition_module.dto.CreateMechanicalConditionRequest;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.CreateMechanicalConditionResponse;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.GroupedMechanicalCondition;
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

    @GetMapping("/exterior")
    public ResponseEntity<List<GroupedMechanicalCondition>> getExteriorConditions() {
        List<GroupedMechanicalCondition> conditions =
            service.getAllConditionsByType(MechanicalConditionType.EXTERIOR);
        return ResponseEntity.ok(conditions);
    }

    @GetMapping("/interior")
    public ResponseEntity<List<GroupedMechanicalCondition>> getInteriorConditions() {
        List<GroupedMechanicalCondition> conditions =
            service.getAllConditionsByType(MechanicalConditionType.INTERIOR);
        return ResponseEntity.ok(conditions);
    }

    @GetMapping("/electrical")
    public ResponseEntity<List<GroupedMechanicalCondition>> getElectricalConditions() {
        List<GroupedMechanicalCondition> conditions =
            service.getAllConditionsByType(MechanicalConditionType.ELECTRICAL_SYSTEM);
        return ResponseEntity.ok(conditions);
    }

    @PostMapping
    public ResponseEntity<CreateMechanicalConditionResponse> createMechanicalCondition(
            @RequestBody CreateMechanicalConditionRequest request
    ) {
        CreateMechanicalConditionResponse response = service.createMechanicalCondition(request);
        return ResponseEntity.ok(response);
    }
}