package com.mechanical_workshop_usm.record_module.record_state.dto.check_in;

import java.util.List;

public record GetCheckInFullResponse(
        Integer checkInId,
        Integer clientId,
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
        List<String> toolNames,
        String entryDate,
        String entryTime,
        Integer mileage,
        String gasLevel,
        String valuables
) {}