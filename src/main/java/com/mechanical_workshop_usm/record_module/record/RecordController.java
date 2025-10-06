package com.mechanical_workshop_usm.record_module.record;

import com.mechanical_workshop_usm.record_module.record.dto.CreateRecordRequest;
import com.mechanical_workshop_usm.record_module.record.dto.CreateRecordResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/record")
public class RecordController {
    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    public ResponseEntity<CreateRecordResponse> createRecord(@RequestBody CreateRecordRequest request) {
        CreateRecordResponse response = recordService.createRecord(request);
        return ResponseEntity.ok(response);
    }
}