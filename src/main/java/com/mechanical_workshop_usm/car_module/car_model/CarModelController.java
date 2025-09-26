package com.mechanical_workshop_usm.car_module.car_model;

import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelRequest;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelResponse;
import com.mechanical_workshop_usm.car_module.car_model.dto.GetCarModelResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car-model")
public class CarModelController {

    private final CarModelService carModelService;
    private final CarModelRepository carModelRepository;

    public CarModelController(CarModelService carModelService, CarModelRepository carModelRepository) {
        this.carModelService = carModelService;
        this.carModelRepository = carModelRepository;
    }

    @GetMapping
    public List<GetCarModelResponse> getAllCarModels() {
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

    @PostMapping
    public ResponseEntity<CreateCarModelResponse> createCarModel(@RequestBody CreateCarModelRequest request) {
        CreateCarModelResponse response = carModelService.createCarModel(request);
        return ResponseEntity.ok(response);
    }
}