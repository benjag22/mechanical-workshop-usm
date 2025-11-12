package com.mechanical_workshop_usm.car_module.car_model;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelRequest;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelResponse;
import com.mechanical_workshop_usm.car_module.car_brand.CarBrand;
import com.mechanical_workshop_usm.car_module.car_brand.CarBrandRepository;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelCheckInRequest;
import com.mechanical_workshop_usm.car_module.car_model.dto.GetCarModelResponse;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void validate(CreateCarModelCheckInRequest createModelCheckInRequest) {
        carModelValidator.validate(createModelCheckInRequest);
    }

    public CreateCarModelResponse createCarModel(CreateCarModelRequest createCarModelRequest) {
        carModelValidator.validateOnCreate(createCarModelRequest);

        CarBrand brand = carBrandRepository.findById(createCarModelRequest.brandId())
                .orElseThrow(() -> new MultiFieldException(
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

    public List<GetCarModelResponse> getAllCarsModels() {
        return carModelRepository.findAll().stream()
                .map(carModel -> new GetCarModelResponse(
                        carModel.getId(),
                        carModel.getModelName(),
                        carModel.getModelType(),
                        carModel.getModelYear(),
                        carModel.getBrand().getId(),
                        carModel.getBrand().getBrandName()
                ))
                .toList();
    }

    public GetCarModelResponse getCarModel(int carModelId) {
        return carModelRepository.findById(carModelId)
                .map(carModel -> new GetCarModelResponse(
                        carModel.getId(),
                        carModel.getModelName(),
                        carModel.getModelType(),
                        carModel.getModelYear(),
                        carModel.getBrand().getId(),
                        carModel.getBrand().getBrandName()
                ))
                .orElseThrow(() -> new MultiFieldException(
                        "Car model not found",
                        List.of(new FieldErrorResponse("car_model_id", "No car model found for the provided ID"))
                ));
    }
}