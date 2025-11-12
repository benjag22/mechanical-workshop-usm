package com.mechanical_workshop_usm.work_order_module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderFullRequest;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderController {

    private final WorkOrderService service;

    public WorkOrderController(WorkOrderService service) {
        this.service = service;
    }

    @PostMapping(path = "/full", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateWorkOrderResponse> createFull(
            @RequestParam("payload") String payloadJson,
            @RequestPart(value = "carPictures", required = false) List<MultipartFile> carPictures,
            @RequestPart(value = "signature", required = false) MultipartFile signature
    ) throws JsonProcessingException {
        CreateWorkOrderFullRequest payload = new ObjectMapper().readValue(payloadJson, CreateWorkOrderFullRequest.class);
        CreateWorkOrderResponse resp = service.createFull(payload, carPictures, signature);
        return ResponseEntity.ok(resp);
    }
}