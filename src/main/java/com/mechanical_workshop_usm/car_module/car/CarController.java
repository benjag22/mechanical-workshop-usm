package com.mechanical_workshop_usm.car_module.car;

import com.mechanical_workshop_usm.car_module.car.dto.CreateCarRequest;
import com.mechanical_workshop_usm.car_module.car.dto.CreateCarResponse;
import com.mechanical_workshop_usm.car_module.car.dto.GetCarFullResponse;
import com.mechanical_workshop_usm.car_module.car.dto.GetCarResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<GetCarResponse>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/patents")
    public ResponseEntity<List<String>> getAllPatents() {
        List<String> response = carService.getAllPatents();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateCarResponse> createCar(@RequestBody CreateCarRequest request) {
        CreateCarResponse response = carService.createCar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCarFullResponse> getCarFullById(@PathVariable int id) {
        GetCarFullResponse response = carService.getCarById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{patent}")
    public ResponseEntity<GetCarFullResponse> getCarFullByPatent(@PathVariable String patent) {
        GetCarFullResponse response = carService.getCarByPatent(patent);
        return ResponseEntity.ok(response);
    }
}