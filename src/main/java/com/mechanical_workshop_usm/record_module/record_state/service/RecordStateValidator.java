package com.mechanical_workshop_usm.record_module.record_state.service;

import com.mechanical_workshop_usm.record_module.record_state.dto.record_state.CreateRecordStateRequest;
import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
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

        if (request.entryDate() == null || request.entryDate().isBlank()) {
            errors.add(new FieldErrorResponse("entry_date", "Entry date is required"));
        } else {
            try {
                LocalDate.parse(request.entryDate());
            } catch (DateTimeParseException ex) {
                errors.add(new FieldErrorResponse("entry_date", "Invalid entry_date format, expected yyyy-MM-dd"));
            }
        }

        if (request.entryTime() == null || request.entryTime().isBlank()) {
            errors.add(new FieldErrorResponse("entry_time", "Entry time is required"));
        } else {
            try {
                LocalTime.parse(request.entryTime());
            } catch (DateTimeParseException ex) {
                errors.add(new FieldErrorResponse("entry_time", "Invalid entry_time format, expected HH:mm[:ss]"));
            }
        }

        if (request.mileage() < 0) {
            errors.add(new FieldErrorResponse("mileage", "Mileage must be non-negative"));
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Invalid record state create request", errors);
        }
    }
}