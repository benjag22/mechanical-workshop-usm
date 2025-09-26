package com.mechanical_workshop_usm.car_module.car_brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, Integer> {
    Optional<CarBrand> findByBrandName(String brandName);
}