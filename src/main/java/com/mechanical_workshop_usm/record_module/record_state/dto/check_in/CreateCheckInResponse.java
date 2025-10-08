package com.mechanical_workshop_usm.record_module.record_state.dto.check_in;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreateCheckInResponse(
        @JsonProperty("id") int id,
        @JsonProperty("entry_date") String entryDate,
        @JsonProperty("entry_time") String entryTime,
        @JsonProperty("mileage") int mileage,
        @JsonProperty("gas_level") String gasLevel,
        @JsonProperty("valuables") String valuables,
        // agregar una lista de condicioens mecanicas del check in
        @JsonProperty("tool_ids") List<Integer> toolIds
) {}