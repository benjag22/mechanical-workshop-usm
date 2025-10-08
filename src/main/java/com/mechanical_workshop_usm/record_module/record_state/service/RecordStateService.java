package com.mechanical_workshop_usm.record_module.record_state.service;

import com.mechanical_workshop_usm.record_module.record_state.dto.GetRecordStateResponse;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.RecordState;
import com.mechanical_workshop_usm.record_module.record_state.persistence.repository.RecordStateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordStateService {
    private final RecordStateRepository recordStateRepository;

    public RecordStateService(RecordStateRepository recordStateRepository) {
        this.recordStateRepository = recordStateRepository;
    }

    public List<GetRecordStateResponse> getAllRecordStates() {
        return recordStateRepository.findAll().stream()
                .map(state -> new GetRecordStateResponse(
                        state.getId(),
                        state.getRecord().getId(),
                        state.getEntryDate().toString(),
                        state.getEntryTime().toString(),
                        state.getMileage()
                ))
                .toList();
    }

    public List<GetRecordStateResponse> getRecordStatesByRecordId(int recordId) {
        return recordStateRepository.findByRecordId(recordId).stream()
                .map(state -> new GetRecordStateResponse(
                        state.getId(),
                        state.getRecord().getId(),
                        state.getEntryDate().toString(),
                        state.getEntryTime().toString(),
                        state.getMileage()
                ))
                .toList();
    }
}