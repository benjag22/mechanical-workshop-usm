package com.mechanical_workshop_usm.mechanical_condition_module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MechanicalConditionResponse(
        @JsonProperty("id") Integer id,
        @JsonProperty("part_name") String partName,
        @JsonProperty("part_condition_state") String partConditionState,
        @JsonProperty("condition_type") String conditionType
) {}