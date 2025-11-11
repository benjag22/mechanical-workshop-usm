package com.mechanical_workshop_usm.mechanical_condition_module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateMechanicalConditionRequest(
        String partName,
        String partConditionState,
        String conditionType
) {}