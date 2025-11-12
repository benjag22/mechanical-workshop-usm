package com.mechanical_workshop_usm.car_module.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    boolean existsByVIN(String VIN);
    boolean existsByLicensePlate(String licensePlate);
    Optional<Car> findByLicensePlate(String patent);
}