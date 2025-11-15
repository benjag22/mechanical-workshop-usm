package com.mechanical_workshop_usm.work_service_module;

import com.mechanical_workshop_usm.work_service_module.dto.CreateWorkServiceRequest;
import com.mechanical_workshop_usm.work_service_module.dto.CreateWorkServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-services")
@Tag(name = "Work Services", description = "CRUD operations for work services")
public class WorkServiceController {

    private final WorkServiceManager service;

    public WorkServiceController(WorkServiceManager service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new work service",
            description = "Creates a new service specifying its name and estimated duration.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Service created successfully",
                            content = @Content(schema = @Schema(implementation = CreateWorkServiceResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error")
            }
    )
    public ResponseEntity<CreateWorkServiceResponse> create(
            @RequestBody CreateWorkServiceRequest request
    ) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Retrieve all work services",
            description = "Returns a list of all existing work services.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List returned successfully"),
            }
    )
    public ResponseEntity<List<CreateWorkServiceResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
