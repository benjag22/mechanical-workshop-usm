package com.mechanical_workshop_usm.mechanical_condition_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Mechanical condition info (id, type, part name and part state)")
public record MechanicalConditionInfo(
        @Schema(description = "Condition id in its own table") Integer id,
        @Schema(description = "Condition type: 'exterior'|'interior'|'electrical'") String type,
        @Schema(description = "Part name") String partName,
        @Schema(description = "Part condition state") String partState
) {}