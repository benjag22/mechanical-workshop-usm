package com.mechanical_workshop_usm.car_module.car_model;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelRequest;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarModelValidator {
    public void validateOnCreate(CreateCarModelRequest request) {

        List<FieldErrorResponse> errors = new ArrayList<>();

        String modelName = request.modelName();
        int modelYear = request.modelYear();
        long brandId = request.brandId();

        if (modelName == null || modelName.isBlank()) {
            errors.add(new FieldErrorResponse("model_name", "The model name cannot be empty or blank"));
        }

        int currentYear = Year.now().getValue();
        if (modelYear < 1886 || modelYear > currentYear) {
            errors.add(new FieldErrorResponse(
                    "model_year",
                    "The model year must be between 1886 and " + currentYear
            ));
        }

        if (brandId <= 0) {
            errors.add(new FieldErrorResponse("brand_id", "The brand id must be provided and greater than 0"));
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Some error in fields", errors);
        }
    }
}