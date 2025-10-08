package com.mechanical_workshop_usm.tool_module.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateToolRequest(
        @JsonProperty("tool_name") String toolName
) {}