package com.mechanical_workshop_usm.car_module.car_brand;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarBrandValidator {
    public void validateOnCreate(CreateCarBrandRequest request) {

        List<FieldErrorResponse> errors = new ArrayList<>();

        String brandName = request.brandName();

        if (brandName == null || brandName.isEmpty()) {
            errors.add(new FieldErrorResponse("brandName", "The brand name cannot be empty or blank"));
        }
        if (!errors.isEmpty()) {
            throw new MultiFieldException("Some error in fields", errors);
        }
    }
}