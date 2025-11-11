package com.mechanical_workshop_usm.record_module.record_state.dto.record_state;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetRecordStateResponse(
        @JsonProperty("id") int id,
        @JsonProperty("record_id") int recordId,
        @JsonProperty("entry_date") String entryDate,
        @JsonProperty("entry_time") String entryTime,
        @JsonProperty("mileage") int mileage
) {}