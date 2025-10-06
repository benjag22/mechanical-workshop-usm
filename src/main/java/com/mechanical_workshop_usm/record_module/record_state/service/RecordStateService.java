package com.mechanical_workshop_usm.record_module.record_state.service;

import com.mechanical_workshop_usm.record_module.record.RecordRepository;
import com.mechanical_workshop_usm.record_module.record_state.dto.CreateRecordStateRequest;
import com.mechanical_workshop_usm.record_module.record_state.dto.CreateRecordStateResponse;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.*;
import com.mechanical_workshop_usm.record_module.record_state.persistence.repository.RecordStateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class RecordStateService {
    private final RecordStateRepository recordStateRepository;
    private final RecordRepository recordRepository;
    private final RecordStateValidator recordStateValidator;

    public RecordStateService(
            RecordStateRepository recordStateRepository,
            RecordRepository recordRepository,
            RecordStateValidator recordStateValidator
    ) {
        this.recordStateRepository = recordStateRepository;
        this.recordRepository = recordRepository;
        this.recordStateValidator = recordStateValidator;
    }

    public CreateRecordStateResponse createRecordState(CreateRecordStateRequest request) {
        recordStateValidator.validateOnCreate(request);

        var record = recordRepository.findById(request.recordId())
                .orElseThrow(() -> new RuntimeException("Record not found"));

        LocalDate entryDate = LocalDate.parse(request.entryDate(), DateTimeFormatter.ISO_DATE);
        LocalTime entryTime = LocalTime.parse(request.entryTime(), DateTimeFormatter.ISO_TIME);

        RecordState state;
        if (request.stateType().equalsIgnoreCase("checkin")) {
            state = new CheckIn(
                    GasLevel.valueOf(request.gasLevel().toUpperCase().replace("/", "_")),
                    request.valuables(),
                    java.sql.Date.valueOf(entryDate),
                    entryTime,
                    request.mileage()
            );
            state.setRecord(record);
        } else { // checkout
            state = new CheckOut(
                    request.vehicleDiagnosis(),
                    request.rating(),
                    java.sql.Date.valueOf(entryDate),
                    entryTime,
                    request.mileage()
            );
            state.setRecord(record);
        }

        var saved = recordStateRepository.save(state);

        return new CreateRecordStateResponse(
                saved.getId(),
                record.getId(),
                entryDate.toString(),
                entryTime.toString(),
                saved.getMileage(),
                request.stateType()
        );
    }
}