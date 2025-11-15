package com.mechanical_workshop_usm.work_order_module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderRequest;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Tag(name = "Work Order")
@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderController {

    private final WorkOrderService service;
    private final ObjectMapper objectMapper;

    public WorkOrderController(WorkOrderService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @PostMapping(
            path = "/full",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create a full work order",
            description = "Send a multipart request: the 'payload' part must be application/json (the JSON body), and carPictures/signature as file parts."
    )
    public ResponseEntity<CreateWorkOrderResponse> createFull(
            @Parameter(
                    description = "JSON payload describing the work order (application/json).",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateWorkOrderRequest.class))
            )
            @RequestPart("payload")
            CreateWorkOrderRequest payload,

            @Parameter(
                    description = "Optional car pictures (one or more files). Send multiple parts named 'carPictures'.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                            array = @ArraySchema(schema = @Schema(type = "string", format = "binary"))
                    )
            )
            @RequestPart(value = "carPictures", required = false)
            List<MultipartFile> carPictures,

            @Parameter(
                    description = "Mechanic signature image (single file)",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(type = "string", format = "binary"))
            )
            @RequestPart(value = "signature", required = false)
            MultipartFile signature
    ) {
        CreateWorkOrderResponse resp = service.createFull(payload, carPictures, signature);
        return ResponseEntity.ok(resp);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all work orders", description = "Returns a list of all work orders.")
    public ResponseEntity<List<CreateWorkOrderResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}