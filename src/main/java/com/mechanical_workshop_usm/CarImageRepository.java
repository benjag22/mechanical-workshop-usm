package com.mechanical_workshop_usm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarImageRepository extends JpaRepository<CarImageModel, Integer> {
    List<CarImageModel> findByWorkOrder_Id(Integer workOrderId);

    boolean existsCarImageModelByImage_Id(Integer imageId);
}
