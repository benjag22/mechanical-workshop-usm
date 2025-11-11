package com.mechanical_workshop_usm.car_module.car_brand;

import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandRequest;
import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandResponse;
import com.mechanical_workshop_usm.car_module.car_brand.dto.GetCarBrandRepsonse;
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

    public void validate(CreateCarBrandRequest createCarBrandRequest) {
        carBrandValidator.validate(createCarBrandRequest);
    }

    public CreateCarBrandResponse createBrand(CreateCarBrandRequest createCarBrandRequest) {
        carBrandValidator.validateOnCreate(createCarBrandRequest);

        CarBrand carBrand = new CarBrand(
                createCarBrandRequest.brandName()
        );

        CarBrand savedCarBrand = carBrandRepository.save(carBrand);

        return new CreateCarBrandResponse(savedCarBrand.getId(), savedCarBrand.getBrandName());
    }

    public List<GetCarBrandRepsonse> getAllCarsBrands() {
        return carBrandRepository.findAll().stream()
                .map(carBrand -> new GetCarBrandRepsonse(
                        carBrand.getId(),
                        carBrand.getBrandName()
                ))
                .toList();
    }
}