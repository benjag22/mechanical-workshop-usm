package com.mechanical_workshop_usm.record_module.record_state.dto.check_in;

import com.mechanical_workshop_usm.mechanical_condition_module.dto.MechanicalConditionInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GetCheckInFullResponse(
        @Schema(description = "Record id")
        int recordId,

        @Schema(description = "Check-in id")
        int checkInId,

        @Schema(description = "Client id")
        int clientId,

        @Schema(description = "Client full name")
        String clientName,

        @Schema(description = "Client email")
        String clientEmail,

        @Schema(description = "Car brand name")
        String brandName,

        @Schema(description = "Car model name")
        String modelName,

        @Schema(description = "Car model type")
        String modelType,

        @Schema(description = "Car model year")
        int modelYear,

        @Schema(description = "Car license plate")
        String licensePlate,

        @Schema(description = "Record reason")
        String reason,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String observations,

        @Schema(description = "Mechanical conditions (id, type, part name, part state)")
        List<MechanicalConditionInfo> mechanicalConditions,

        @Schema(description = "Tools names")
        List<String> tools,

        @Schema(description = "Entry time as ISO datetime string")
        String entryTime,

        @Schema(description = "Mileage")
        int mileage,

        @Schema(description = "Gas level")
        String gasLevel,

        @Schema(description = "Valuables declared")
        String valuables
) {}