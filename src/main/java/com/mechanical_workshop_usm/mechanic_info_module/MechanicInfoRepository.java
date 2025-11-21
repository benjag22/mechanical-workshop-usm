package com.mechanical_workshop_usm.mechanic_info_module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MechanicInfoRepository extends JpaRepository<MechanicInfo, Integer> {
    boolean existsByRut(String rut);

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    default MechanicInfo getByIdOrThrow(Integer id) {
        return findById(id).orElseThrow(() -> new IllegalStateException("MechanicInfo no encontrado con id: " + id));
    }
}