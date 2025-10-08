package com.mechanical_workshop_usm.tool_module;

import com.mechanical_workshop_usm.tool_module.dto.CreateToolRequest;
import com.mechanical_workshop_usm.tool_module.dto.CreateToolResponse;
import com.mechanical_workshop_usm.tool_module.dto.GetToolResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tool")
public class ToolController {
    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @PostMapping
    public ResponseEntity<CreateToolResponse> createTool(@RequestBody CreateToolRequest request) {
        CreateToolResponse response = toolService.createTool(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<GetToolResponse> getAllTools() {
        return toolService.getAllTools();
    }
}