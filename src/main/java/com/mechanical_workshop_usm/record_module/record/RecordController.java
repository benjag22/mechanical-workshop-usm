package com.mechanical_workshop_usm.record_module.record;

import com.mechanical_workshop_usm.record_module.record.dto.CreateRecordRequest;
import com.mechanical_workshop_usm.record_module.record.dto.CreateRecordResponse;
import com.mechanical_workshop_usm.record_module.record.dto.GetRecordResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<GetRecordResponse> getAllRecords() {
        return recordService.getAllRecords();
    }
}
