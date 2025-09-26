package com.mechanical_workshop_usm.car_module.car_model;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelRequest;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelResponse;
import com.mechanical_workshop_usm.car_module.car_brand.CarBrand;
import com.mechanical_workshop_usm.car_module.car_brand.CarBrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarModelService {
    private final CarModelRepository carModelRepository;
    private final CarModelValidator carModelValidator;
    private final CarBrandRepository carBrandRepository;

    public CarModelService(CarModelRepository carModelRepository, CarModelValidator carModelValidator, CarBrandRepository carBrandRepository) {
        this.carModelRepository = carModelRepository;
        this.carModelValidator = carModelValidator;
        this.carBrandRepository = carBrandRepository;
    }

    public CreateCarModelResponse createCarModel(CreateCarModelRequest createCarModelRequest) {
        carModelValidator.validateOnCreate(createCarModelRequest);

        carModelRepository.findAll().stream()
                .filter(cm -> cm.getModelName().equalsIgnoreCase(createCarModelRequest.modelName()) && cm.getBrand().getId() == createCarModelRequest.brandId())
                .findAny()
                .ifPresent(existingModel -> {
                    throw new MultiFieldException(
                            "Some error in fields",
                            List.of(new FieldErrorResponse("model_name", "A car model with this name already exists for the selected brand"))
                    );
                });

        Optional<CarBrand> brandOpt = carBrandRepository.findById(createCarModelRequest.brandId());
        CarBrand brand = brandOpt.orElseThrow(() -> new MultiFieldException(
                "Some error in fields",
                List.of(new FieldErrorResponse("brand_id", "Brand not found"))
        ));

        CarModel carModel = new CarModel(
                createCarModelRequest.modelName(),
                createCarModelRequest.modelType(),
                createCarModelRequest.modelYear(),
                brand
        );
        CarModel savedCarModel = carModelRepository.save(carModel);
        return new CreateCarModelResponse(
                savedCarModel.getId(),
                savedCarModel.getModelName(),
                savedCarModel.getModelType(),
                savedCarModel.getModelYear()
        );
    }
}