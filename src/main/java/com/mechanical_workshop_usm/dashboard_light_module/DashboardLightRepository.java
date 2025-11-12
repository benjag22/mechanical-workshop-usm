package com.mechanical_workshop_usm.dashboard_light_module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardLightRepository extends JpaRepository<DashboardLight, Integer> {
}