package com.mechanical_workshop_usm.record_module.record_state.persistence.repository;


import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, Integer> {
    boolean existsByRecord_Id(int recordId);

    Optional<Object> findByRecord_Id(int id);
}