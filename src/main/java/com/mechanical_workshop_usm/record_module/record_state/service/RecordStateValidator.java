package com.mechanical_workshop_usm.record_module.record_state.service;

import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.RecordState;
import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecordStateValidator {
    public void validate(RecordState recordState) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        if (recordState.getEntryDate() == null) {
            errors.add(new FieldErrorResponse("entry_date", "Entry date is required"));
        }
        if (recordState.getEntryTime() == null) {
            errors.add(new FieldErrorResponse("entry_time", "Entry time is required"));
        }
        if (recordState.getMileage() < 0) {
            errors.add(new FieldErrorResponse("mileage", "Mileage must be non-negative"));
        }
        if (recordState.getRecord() == null) {
            errors.add(new FieldErrorResponse("record", "Record is required"));
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Invalid record state fields", errors);
        }
    }
}