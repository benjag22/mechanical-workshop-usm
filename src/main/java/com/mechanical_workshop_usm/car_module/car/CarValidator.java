package com.mechanical_workshop_usm.car_module.car;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.car_module.car.dto.CreateCarRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarValidator {
    private final CarRepository carRepository;

    public CarValidator(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void validateOnCreate(CreateCarRequest request) {

        List<FieldErrorResponse> errors = new ArrayList<>();

        if (request.VIN() == null || request.VIN().isBlank()) {
            errors.add(new FieldErrorResponse("vin", "VIN cannot be empty or blank"));
        } else if (carRepository.existsByVIN(request.VIN())) {
            errors.add(new FieldErrorResponse("vin", "VIN already exists"));
        }

        if (request.licensePlate() == null || request.licensePlate().isBlank()) {
            errors.add(new FieldErrorResponse("license_plate", "License plate cannot be empty or blank"));
        } else if (carRepository.existsByLicensePlate(request.licensePlate())) {
            errors.add(new FieldErrorResponse("license_plate", "License plate already exists"));
        }

        if (request.modelId() <= 0) {
            errors.add(new FieldErrorResponse("model_id", "Model ID must be greater than 0"));
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Some error in fields", errors);
        }
    }
}