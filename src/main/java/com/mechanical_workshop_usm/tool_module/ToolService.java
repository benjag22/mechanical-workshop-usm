package com.mechanical_workshop_usm.tool_module;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.tool_module.dto.CreateToolRequest;
import com.mechanical_workshop_usm.tool_module.dto.SingleToolResponse;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@Service
public class ToolService {
    private final ToolRepository toolRepository;
    private final ToolValidator toolValidator;

    public ToolService(ToolRepository toolRepository, ToolValidator toolValidator) {
        this.toolRepository = toolRepository;
        this.toolValidator = toolValidator;
    }

    public void validateOnCreate(CreateToolRequest createToolRequest) {
        toolValidator.validateOnCreate(createToolRequest);
    }

    public SingleToolResponse createTool(CreateToolRequest createToolRequest) throws MultiFieldException {
        toolValidator.validateOnCreate(createToolRequest);

        Tool tool = new Tool(
                createToolRequest.name()
        );

        try {
            Tool savedTool = toolRepository.save(tool);
            return new SingleToolResponse(savedTool.getId(), savedTool.getToolName());
        } catch (DataIntegrityViolationException e) {
            throw new MultiFieldException(
                    "Invalid tool fields",
                    List.of(new FieldErrorResponse("tool_name", "A tool with this name already exists (concurrent error)"))
            );
        }
    }

    public List<SingleToolResponse> getAllTools() {
        return toolRepository.findAll().stream()
                .map(tool -> new SingleToolResponse(tool.getId(), tool.getToolName()))
                .toList();
    }
}