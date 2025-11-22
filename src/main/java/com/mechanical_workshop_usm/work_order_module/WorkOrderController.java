package com.mechanical_workshop_usm.work_order_module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mechanical_workshop_usm.api.exceptions.ExceptionMapper;
import com.mechanical_workshop_usm.work_order_module.dto.*;
import com.mechanical_workshop_usm.work_order_realized_service_module.WorkOrderRealizedServiceService;
import com.mechanical_workshop_usm.work_order_realized_service_module.dto.CreateWorkOrderRealizedServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Set;


@Tag(name = "Work Order")
@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderController {
    private final WorkOrderService service;
    private final ObjectMapper objectMapper;
    private final Validator validator;
    private final ExceptionMapper exceptionMapper;
    private final WorkOrderRealizedServiceService realizedServiceService;

    public WorkOrderController(WorkOrderService service, ObjectMapper objectMapper, Validator validator, ExceptionMapper exceptionMapper, WorkOrderRealizedServiceService realizedServiceService) {
        this.service = service;
        this.objectMapper = objectMapper;
        this.validator = validator;
        this.exceptionMapper = exceptionMapper;
        this.realizedServiceService = realizedServiceService;
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
            @Schema(implementation = CreateWorkOrderRequest.class, description = "Configuration update")
            String payload,

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
        final CreateWorkOrderRequest request = parseCreateWorkOrderRequest(payload);

        final Set<ConstraintViolation<CreateWorkOrderRequest>> errors = validator.validate(request);

        if (!errors.isEmpty()) {
            throw exceptionMapper.fromValidationErrors(errors);
        }

        final CreateWorkOrderResponse resp = service.createFull(request, carPictures, signature);
        return ResponseEntity.ok(resp);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all work orders", description = "Returns a list of all work orders.")
    public ResponseEntity<List<TrimmedWorkOrder>> getAllWorkOrders() {
        List<TrimmedWorkOrder> response = service.getTrimmedWorkOrders();
        return ResponseEntity.ok(response);
    }

    private CreateWorkOrderRequest parseCreateWorkOrderRequest(String json) {
        if (json == null || json.isBlank()) {
            throw new RuntimeException("Config update payload json cannot be empty");
        }

        try {
            return objectMapper.readValue(
                    json, new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw exceptionMapper.fromJsonProcessingException(e);
        }
    }

    @Operation(
            summary = "Get full work order by ID",
            description = "Obtiene todos los datos detallados de la orden de trabajo por su ID."
    )
    @GetMapping(
            path = "/{id}/full",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GetWorkOrderFull> getWorkOrderFullById(
            @Parameter(description = "ID de la orden de trabajo", required = true)
            @PathVariable Integer id
    ) {
        try {
            GetWorkOrderFull workOrderFull = service.getFullById(id);
            return ResponseEntity.ok(workOrderFull);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}/authorized/{rut}")
    public ResponseEntity<Boolean> isAuthorized(
        @PathVariable Integer id,
        @PathVariable String rut
    ) {
        boolean result = service.isLeaderAuthorized(id, rut);
        return ResponseEntity.ok(result);
    }


    @PatchMapping(
            path = "/{workOrderId}/realized-services/toggle",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CreateWorkOrderRealizedServiceResponse>> toggleRealizedServicesFinalized(
            @PathVariable Integer workOrderId,
            @RequestBody List<Integer> workServiceIds
    ) {
        List<CreateWorkOrderRealizedServiceResponse> response = realizedServiceService.toggleFinalizedForServices(workOrderId, workServiceIds);
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{workOrderId}/complete")
    public ResponseEntity<BasicWorkOrderInfo> markWorkOrderAsCompleted(@PathVariable Integer workOrderId) {
        BasicWorkOrderInfo result = service.markWorkOrderAsCompleted(workOrderId);
        return ResponseEntity.ok(result);
    }
}