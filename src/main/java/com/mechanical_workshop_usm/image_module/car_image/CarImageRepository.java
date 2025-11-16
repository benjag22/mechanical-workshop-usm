package com.mechanical_workshop_usm.image_module.car_image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarImageRepository extends JpaRepository<CarImage, Integer> {
    List<CarImage> findByWorkOrder_Id(Integer workOrderId);

    boolean existsCarImageModelByImage_Id(Integer imageId);
}
