package com.mechanical_workshop_usm.record_module.record_state.service;

import com.mechanical_workshop_usm.record_module.record_state.dto.CreateRecordStateRequest;
import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecordStateValidator {
    public void validateOnCreate(CreateRecordStateRequest request) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        if (request.recordId() <= 0) {
            errors.add(new FieldErrorResponse("record_id", "Record ID must be valid"));
        }
        if (request.entryDate() == null || request.entryDate().isBlank()) {
            errors.add(new FieldErrorResponse("entry_date", "Entry date cannot be empty"));
        }
        if (request.entryTime() == null || request.entryTime().isBlank()) {
            errors.add(new FieldErrorResponse("entry_time", "Entry time cannot be empty"));
        }
        if (request.mileage() < 0) {
            errors.add(new FieldErrorResponse("mileage", "Mileage must be non-negative"));
        }
        if (request.stateType() == null || !(request.stateType().equalsIgnoreCase("checkin") || request.stateType().equalsIgnoreCase("checkout"))) {
            errors.add(new FieldErrorResponse("state_type", "State type must be 'checkin' or 'checkout'"));
        }
        if (request.stateType().equalsIgnoreCase("checkin")) {
            if (request.gasLevel() == null || request.gasLevel().isBlank()) {
                errors.add(new FieldErrorResponse("gas_level", "Gas level is required for checkin"));
            }
        }
        if (request.stateType().equalsIgnoreCase("checkout")) {
            if (request.vehicleDiagnosis() == null || request.vehicleDiagnosis().isBlank()) {
                errors.add(new FieldErrorResponse("vehicle_diagnosis", "Vehicle diagnosis is required for checkout"));
            }
            if (request.rating() == null) {
                errors.add(new FieldErrorResponse("rating", "Rating is required for checkout"));
            }
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Invalid record state fields", errors);
        }
    }
}