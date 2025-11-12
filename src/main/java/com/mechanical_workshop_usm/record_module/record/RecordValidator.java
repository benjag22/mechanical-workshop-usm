package com.mechanical_workshop_usm.record_module.record;

import com.mechanical_workshop_usm.record_module.record.dto.CreateRecordRequest;
import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecordValidator {
    public void validateOnCreate(CreateRecordRequest request) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        if (request.reason() == null || request.reason().isBlank()) {
            errors.add(new FieldErrorResponse("reason", "Reason cannot be empty"));
        }
        if (request.carId() <= 0) {
            errors.add(new FieldErrorResponse("car_id", "Car ID must be valid"));
        }
        if (request.clientInfoId() <= 0) {
            errors.add(new FieldErrorResponse("client_info_id", "Client Info ID must be valid"));
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Invalid record fields", errors);
        }
    }
}
