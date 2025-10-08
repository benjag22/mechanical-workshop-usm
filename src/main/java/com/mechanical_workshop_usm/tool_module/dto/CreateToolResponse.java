package com.mechanical_workshop_usm.tool_module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateToolResponse(
        @JsonProperty("id") int id,
        @JsonProperty("tool_name") String toolName
) {}