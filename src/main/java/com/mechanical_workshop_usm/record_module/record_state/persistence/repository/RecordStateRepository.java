package com.mechanical_workshop_usm.record_module.record_state.persistence.repository;

import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.RecordState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordStateRepository extends JpaRepository<RecordState, Integer> {
    List<RecordState> findByRecordId(int recordId);
}
