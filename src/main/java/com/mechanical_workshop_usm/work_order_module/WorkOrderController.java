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
            description = "Send JSON in 'payload' and multiple carPictures[] files."
    )
    public ResponseEntity<CreateWorkOrderResponse> createFull(
            @Parameter(
                    description = "JSON payload describing the work order",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateWorkOrderRequest.class)
                    )
            )
            @RequestPart("payload")
            String payloadJson,

            @Parameter(
                    description = "Multiple car pictures",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            array = @ArraySchema(schema = @Schema(type = "string", format = "binary"))
                    )
            )
            @RequestPart(value = "carPictures", required = false)
            List<MultipartFile> carPictures,

            @RequestPart(value = "signature", required = false)
            MultipartFile signature
    ) throws JsonProcessingException {

        CreateWorkOrderRequest payload = objectMapper.readValue(payloadJson, CreateWorkOrderRequest.class);

        CreateWorkOrderResponse resp = service.createFull(payload, carPictures, signature);
        return ResponseEntity.ok(resp);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all work orders", description = "Returns a list of all work orders.")
    public ResponseEntity<List<CreateWorkOrderResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}