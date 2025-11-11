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

        @JsonProperty("client_id") int clientId,
        @JsonProperty("car_id") int carId,
        @JsonProperty("mechanical_conditions_ids") List<Integer> MechanicalConditionsIds,
        @JsonProperty("tools_ids") List<Integer> toolsIds

) {}
