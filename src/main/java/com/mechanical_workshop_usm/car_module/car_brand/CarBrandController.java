package com.mechanical_workshop_usm.car_module.car_brand;

import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandRequest;
import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandResponse;
import com.mechanical_workshop_usm.car_module.car_brand.dto.GetCarBrandRepsonse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car-brand")
public class CarBrandController {
    private final CarBrandService carBrandService;

    public CarBrandController(CarBrandService mechanicInfoService) {
        this.carBrandService = mechanicInfoService;
    }

    @GetMapping
    public List<GetCarBrandRepsonse> getAllCarBrands() {
        return carBrandService.getAllCarsBrands();
    }

    @PostMapping
    public ResponseEntity<CreateCarBrandResponse> createMechanic(@RequestBody CreateCarBrandRequest createCarBrandRequest) {
        CreateCarBrandResponse createCarBrandResponse = carBrandService.createBrand(createCarBrandRequest);
        return ResponseEntity.ok(createCarBrandResponse);
    }
}
