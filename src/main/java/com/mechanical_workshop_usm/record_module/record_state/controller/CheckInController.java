package com.mechanical_workshop_usm.record_module.record_state.controller;

import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.CreateCheckInRequest;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.CreateCheckInResponse;
import com.mechanical_workshop_usm.record_module.record_state.service.check_in.CheckInService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkin")
public class CheckInController {
    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping
    public ResponseEntity<CreateCheckInResponse> createCheckIn(@RequestBody CreateCheckInRequest request) {
        CreateCheckInResponse response = checkInService.createCheckIn(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CreateCheckInResponse>> getAllCheckIns() {
        List<CreateCheckInResponse> list = checkInService.getAllCheckIns();
        return ResponseEntity.ok(list);
    }
}