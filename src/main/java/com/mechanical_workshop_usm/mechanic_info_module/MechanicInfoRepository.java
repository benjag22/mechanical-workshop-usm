package com.mechanical_workshop_usm.mechanic_info_module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MechanicInfoRepository extends JpaRepository<MechanicInfo, Integer> {
}