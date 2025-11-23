package com.mechanical_workshop_usm.record_module.record_state.controller;


import com.mechanical_workshop_usm.record_module.record_state.dto.check_out.CreateCheckOutRequest;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_out.CheckOutResponse;
import com.mechanical_workshop_usm.record_module.record_state.service.check_out.CheckOutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/check-out")
public class CheckOutController {
    private final CheckOutService service;

    public CheckOutController(CheckOutService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CheckOutResponse> createCheckOut(@RequestBody CreateCheckOutRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckOutResponse> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
