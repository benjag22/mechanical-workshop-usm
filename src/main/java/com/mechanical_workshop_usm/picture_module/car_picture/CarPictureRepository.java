package com.mechanical_workshop_usm.picture_module.car_picture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarPictureRepository extends JpaRepository<CarPicture, Integer> {

    List<CarPicture> findByWorkOrder_Id(Integer workOrderId);

    List<CarPicture> findByPath(String path);

    boolean existsByPath(String path);
}