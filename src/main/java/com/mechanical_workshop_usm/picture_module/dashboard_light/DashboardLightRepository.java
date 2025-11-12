package com.mechanical_workshop_usm.picture_module.dashboard_light;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardLightRepository extends JpaRepository<DashboardLight, Integer> {
}