package com.mechanical_workshop_usm.picture_module.picture;

import com.mechanical_workshop_usm.picture_module.dashboard_light.DashboardLight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {
}