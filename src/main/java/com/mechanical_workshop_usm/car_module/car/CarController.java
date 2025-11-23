package com.mechanical_workshop_usm.car_module.car;

import com.mechanical_workshop_usm.car_module.car.dto.*;
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

    @GetMapping("/available")
    public ResponseEntity<List<GetCarState>> getAvailableCarsNoPendingRecords() {
        return ResponseEntity.ok(carService.getCarStates());
    }

    @PostMapping
    public ResponseEntity<CreateCarResponse> createCar(@RequestBody CreateCarRequest request) {
        CreateCarResponse response = carService.createCar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCar> getCarFullById(@PathVariable int id) {
        GetCar response = carService.getCarById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-patent/{patent}")
    public ResponseEntity<GetCar> getCarFullByPatent(@PathVariable String patent) {
        GetCar response = carService.getCarByPatent(patent);
        return ResponseEntity.ok(response);
    }
}