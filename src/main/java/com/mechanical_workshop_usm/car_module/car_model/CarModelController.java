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

    public CarModelController(CarModelService carModelService) {
        this.carModelService = carModelService;
    }

    @GetMapping
    public List<GetCarModelResponse> getAllCarModels() {
        return carModelService.getAllCarsModels();
    }

    @PostMapping
    public ResponseEntity<CreateCarModelResponse> createCarModel(@RequestBody CreateCarModelRequest request) {
        CreateCarModelResponse response = carModelService.createCarModel(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<GetCarModelResponse> getCarModel(@PathVariable int id) {
        GetCarModelResponse response = carModelService.getCarModel(id);
        return ResponseEntity.ok(response);
    }
}