package com.mechanical_workshop_usm.record_module.record_state.controller;

import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.CreateCheckInRequest;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.CreateCheckInResponse;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.GetCheckInBasicResponse;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.GetCheckInFullResponse;
import com.mechanical_workshop_usm.record_module.record_state.service.check_in.CheckInQueryService;
import com.mechanical_workshop_usm.record_module.record_state.service.check_in.CheckInService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Check In")
@RestController
@RequestMapping("/api/checkin")
public class CheckInController {
    private final CheckInService checkInService;
    private final CheckInQueryService checkInQueryService;

    public CheckInController(CheckInService checkInService, CheckInQueryService checkInQueryService) {
        this.checkInService = checkInService;
        this.checkInQueryService = checkInQueryService;
    }

    @PostMapping
    public ResponseEntity<CreateCheckInResponse> createCheckIn(@RequestBody CreateCheckInRequest request) {
        CreateCheckInResponse response = checkInService.createCheckIn(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GetCheckInBasicResponse>> getAllCheckInFull() {
        List<GetCheckInBasicResponse> results = checkInQueryService.getAllCheckInsFull();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<GetCheckInFullResponse> getCheckInFull(@PathVariable Integer id) {
        return ResponseEntity.ok(checkInService.getCheckInById(id));
    }
}