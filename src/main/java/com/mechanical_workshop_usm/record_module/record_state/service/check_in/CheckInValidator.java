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

        if (request == null) {
            errors.add(new FieldErrorResponse("request", "CreateCheckInRequest is required"));
            throw new MultiFieldException("Invalid check-in fields", errors);
        }

        if (request.clientId() != null && request.clientId() <= 0) {
            errors.add(new FieldErrorResponse("clientId", "clientId must be greater than zero when provided"));
        }

        if (request.clientId() == null && request.client() == null) {
            errors.add(new FieldErrorResponse("client", "Client information is required when clientId is not provided"));
        }

        boolean hasCarId = request.carId() != null;
        boolean hasCarObject = request.car() != null;

        boolean hasCarModelId = request.carModelID() != null;
        boolean hasCarModelObject = request.carModel() != null;

        boolean hasCarBrandId = request.carBrandID() != null;
        boolean hasCarBrandObject = request.carBrand() != null;

        if (hasCarId) {
            if (hasCarObject || hasCarModelId || hasCarModelObject || hasCarBrandId || hasCarBrandObject) {
                errors.add(new FieldErrorResponse("car", "Provide either carId (existing car) or the car/carModel/carBrand creation package, not both"));
            }
        } else {
            if (!hasCarObject) {
                errors.add(new FieldErrorResponse("car", "To create a new car you must provide the car object"));
            } else {
                if (hasCarModelId == hasCarModelObject) {
                    if (!hasCarModelId) {
                        errors.add(new FieldErrorResponse("car_model", "Either carModelID or carModel must be provided when creating a car"));
                    } else {
                        errors.add(new FieldErrorResponse("car_model", "Provide only one of carModelID or carModel, not both"));
                    }
                } else if (hasCarModelId) {
                    if (hasCarBrandId || hasCarBrandObject) {
                        errors.add(new FieldErrorResponse("car_brand", "When providing carModelID you must not provide carBrandID or carBrand"));
                    }
                } else {
                    if (hasCarBrandId == hasCarBrandObject) {
                        if (!hasCarBrandId) {
                            errors.add(new FieldErrorResponse("car_brand", "When creating a carModel you must provide either carBrandID or carBrand"));
                        } else {
                            errors.add(new FieldErrorResponse("car_brand", "Provide only one of carBrandID or carBrand, not both"));
                        }
                    }
                }
            }
        }

        if (
                (request.interiorConditionsIds() == null || request.interiorConditionsIds().isEmpty())&&
                (request.exteriorConditionsIds() == null || request.exteriorConditionsIds().isEmpty())&&
                (request.electricalConditionsIds() == null || request.electricalConditionsIds().isEmpty())
        ) {
            errors.add(new FieldErrorResponse("mechanicalConditionsIds", "Enter at least one mechanical condition"));
        }


        if (request.gasLevel() == null || request.gasLevel().isBlank()) {
            errors.add(new FieldErrorResponse("gasLevel", "Gas level is required"));
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
                errors.add(new FieldErrorResponse("gasLevel", "Invalid gas level"));
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