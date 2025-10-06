package com.mechanical_workshop_usm.record_module.record_state.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateRecordStateResponse(
        @JsonProperty("id") int id,
        @JsonProperty("record_id") int recordId,
        @JsonProperty("entry_date") String entryDate,
        @JsonProperty("entry_time") String entryTime,
        @JsonProperty("mileage") int mileage,
        @JsonProperty("state_type") String stateType
) {}