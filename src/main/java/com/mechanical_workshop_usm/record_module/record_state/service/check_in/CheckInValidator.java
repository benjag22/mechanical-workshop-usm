package com.mechanical_workshop_usm.record_module.record_state.service.check_in;

import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.CreateCheckInRequest;
import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.GasLevel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CheckInValidator {
    public void validateOnCreate(CreateCheckInRequest request) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        boolean hasClientId = request.clientId() != null;
        boolean hasClientObject = request.client() != null;
        if (hasClientId == hasClientObject) {
            if (!hasClientId) {
                errors.add(new FieldErrorResponse("client", "Either client_id or client must be provided"));
            } else {
                errors.add(new FieldErrorResponse("client", "Provide only one of client_id or client, not both"));
            }
        }

        boolean hasCarId = request.carId() != null;
        boolean hasCarObject = request.car() != null;
        boolean hasCarModel = request.car_model() != null;
        boolean hasCarBrand = request.car_brand() != null;

        if (hasCarId && (hasCarObject || hasCarModel || hasCarBrand)) {
            errors.add(new FieldErrorResponse("car", "Provide either car_id or the car/car_model/car_brand package, not both"));
        } else if (!hasCarId) {
            if (!(hasCarObject && hasCarModel && hasCarBrand)) {
                errors.add(new FieldErrorResponse("car", "To create a new car you must provide car, car_model and car_brand"));
            }
        }

        if (request.mechanicalConditionsIds() == null || request.mechanicalConditionsIds().isEmpty()) {
            errors.add(new FieldErrorResponse("mechanical_conditions_ids", "At least one mechanical condition id is required"));
        }

        if (request.gasLevel() == null || request.gasLevel().isBlank()) {
            errors.add(new FieldErrorResponse("gas_level", "Gas level is required"));
        } else {
            String normalized = request.gasLevel().trim();
            boolean matched = false;
            for (GasLevel g : GasLevel.values()) {
                if (g.name().equalsIgnoreCase(normalized) || g.toString().equalsIgnoreCase(normalized)) {
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                errors.add(new FieldErrorResponse("gas_level", "Invalid gas level"));
            }
        }

        if (request.valuables() != null && request.valuables().length() > 255) {
            errors.add(new FieldErrorResponse("valuables", "Valuables must be at most 255 characters"));
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Invalid check-in fields", errors);
        }
    }
}