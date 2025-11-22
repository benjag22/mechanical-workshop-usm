package com.mechanical_workshop_usm.record_module.record_state.persistence.repository;


import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, Integer> {
}