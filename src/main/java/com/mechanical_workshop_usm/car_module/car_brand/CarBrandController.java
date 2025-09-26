package com.mechanical_workshop_usm.car_module.car_brand;

import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandRequest;
import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car-brand")
public class CarBrandController {
    private final CarBrandService carBrandService;
    private final CarBrandRepository carBrandRepository;

    public CarBrandController(CarBrandService mechanicInfoService, CarBrandRepository carBrandRepository) {
        this.carBrandService = mechanicInfoService;
        this.carBrandRepository = carBrandRepository;
    }

    @GetMapping
    public List<CarBrand> getAllMechanics() {
        return carBrandRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<CreateCarBrandResponse> createMechanic(@RequestBody CreateCarBrandRequest createCarBrandRequest) {
        CreateCarBrandResponse createCarBrandResponse = carBrandService.createBrand(createCarBrandRequest);
        return ResponseEntity.ok(createCarBrandResponse);
    }
}
