package com.mechanical_workshop_usm.record_module.record_state.persistence.repository;

import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    List<CheckIn> findByRecordId(int recordId);
}
