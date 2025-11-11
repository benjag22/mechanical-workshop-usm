package com.mechanical_workshop_usm.record_module.record_state.service;

import com.mechanical_workshop_usm.record_module.record.dto.CreateRecordRequest;
import com.mechanical_workshop_usm.record_module.record_state.dto.record_state.CreateRecordStateRequest;
import com.mechanical_workshop_usm.record_module.record_state.dto.record_state.GetRecordStateResponse;
import com.mechanical_workshop_usm.record_module.record_state.persistence.repository.RecordStateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordStateService {
    private final RecordStateRepository recordStateRepository;
    private final RecordStateValidator recordStateValidator;

    public RecordStateService(RecordStateRepository recordStateRepository, RecordStateValidator recordStateValidator) {
        this.recordStateRepository = recordStateRepository;
        this.recordStateValidator = recordStateValidator;
    }

    public void validateOnCreate(CreateRecordStateRequest createRecordStateRequest) {
        recordStateValidator.validateOnCreate(createRecordStateRequest);
    }
}
