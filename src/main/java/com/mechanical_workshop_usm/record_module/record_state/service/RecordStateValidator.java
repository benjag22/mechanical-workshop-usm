package com.mechanical_workshop_usm.record_module.record_state.service;

import com.mechanical_workshop_usm.record_module.record_state.dto.record_state.CreateRecordStateRequest;
import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecordStateValidator {
    public void validateOnCreate(CreateRecordStateRequest request) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        if (request == null) {
            errors.add(new FieldErrorResponse("request", "CreateRecordStateRequest is required"));
            throw new MultiFieldException("Invalid record state create request", errors);
        }

        if (request.mileage() < 0) {
            errors.add(new FieldErrorResponse("mileage", "Mileage must be non-negative"));
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Invalid record state create request", errors);
        }
    }
}