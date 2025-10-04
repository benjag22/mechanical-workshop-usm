package com.mechanical_workshop_usm.car_module.car_model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Integer> {
    Optional<CarModel> findByModelName(String brandName);
    Optional<CarModel> findByModelNameAndBrand_Id(String modelName, Long brandId);
}
