package com.mechanical_workshop_usm.record_module.record_state.dto.check_in;

import java.util.List;

public record GetCheckInBasicResponse(
        Integer checkInId,
        String clientName,
        String clientEmail,
        String brandName,
        String modelName,
        String modelType,
        Integer modelYear,
        String licensePlate,
        String reason,
        List<String> mechanicalConditionPartNames,
        List<String> mechanicalConditionStates,
        String entryDate,
        String entryTime
) {}