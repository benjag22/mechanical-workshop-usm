package com.mechanical_workshop_usm.tool_module;

import com.mechanical_workshop_usm.tool_module.dto.CreateToolRequest;
import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ToolValidator {
    private final ToolRepository toolRepository;

    public ToolValidator(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public void validateOnCreate(CreateToolRequest request) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        String toolName = request.name();

        if (request.name() == null || request.name().isBlank()) {
            errors.add(new FieldErrorResponse("tool_name", "Tool name cannot be empty"));
        }

        if (toolRepository.findByToolName(toolName).isPresent()) {
            errors.add(new FieldErrorResponse("tool_name", "A tool  with this name already exists"));
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Invalid tool fields", errors);
        }
    }
}