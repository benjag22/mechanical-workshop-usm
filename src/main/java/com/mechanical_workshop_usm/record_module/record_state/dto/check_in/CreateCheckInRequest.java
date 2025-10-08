package com.mechanical_workshop_usm.record_module.record_state.dto.check_in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.CreateMechanicalConditionRequest;

import java.util.List;

public record CreateCheckInRequest(
        @JsonProperty("record_id") int recordId,
        @JsonProperty("entry_date") String entryDate, // formato yyyy-MM-dd
        @JsonProperty("entry_time") String entryTime, // formato HH:mm:ss
        @JsonProperty("mileage") int mileage,
        @JsonProperty("gas_level") String gasLevel,
        @JsonProperty("valuables") String valuables,
        @JsonProperty("tool_ids") List<CreateMechanicalConditionRequest> MechanicalConditions,
        @JsonProperty("tool_ids") List<Integer> toolIds
) {}