package com.mechanical_workshop_usm.car_module.car_brand;

import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandRequest;
import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandResponse;
import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarBrandService {
    private final CarBrandRepository carBrandRepository;
    private final CarBrandValidator carBrandValidator;

    public CarBrandService(CarBrandRepository carBrandRepository, CarBrandValidator carBrandValidator) {
        this.carBrandRepository = carBrandRepository;
        this.carBrandValidator = carBrandValidator;
    }

    public CreateCarBrandResponse createBrand(CreateCarBrandRequest createCarBrandRequest) {
        carBrandValidator.validateOnCreate(createCarBrandRequest);

        carBrandRepository.findByBrandName(createCarBrandRequest.brandName())
                .ifPresent(existingBrand -> {
                    throw new MultiFieldException(
                            "Some error in fields",
                            List.of(new FieldErrorResponse("brandName", "Brand name already exists"))
                    );
                });

        CarBrand carBrand = new CarBrand(
                createCarBrandRequest.brandName()
        );
        CarBrand savedCarBrand = carBrandRepository.save(carBrand);
        return new CreateCarBrandResponse(savedCarBrand.getId(), savedCarBrand.getBrandName());
    }
}