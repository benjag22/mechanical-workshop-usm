package com.mechanical_workshop_usm.record_module.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
    @EntityGraph(attributePaths = {"recordStates"})
    Optional<Record> findFirstByCar_IdOrderByIdDesc(int carId);
}
