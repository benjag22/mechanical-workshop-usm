package com.mechanical_workshop_usm.tool_module;

import com.mechanical_workshop_usm.tool_module.dto.CreateToolRequest;
import com.mechanical_workshop_usm.tool_module.dto.SingleToolResponse;
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
    public ResponseEntity<SingleToolResponse> createTool(@RequestBody CreateToolRequest request) {
        SingleToolResponse response = toolService.createTool(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SingleToolResponse>> getAllTools() {
        List<SingleToolResponse> response = toolService.getAllTools();
        return ResponseEntity.ok(response);
    }
}