package com.mechanical_workshop_usm.record_module.record_state.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateRecordStateRequest(
        @JsonProperty("record_id") int recordId,
        @JsonProperty("entry_date") String entryDate, // ISO date string (yyyy-MM-dd)
        @JsonProperty("entry_time") String entryTime, // ISO time string (HH:mm:ss)
        @JsonProperty("mileage") int mileage,
        @JsonProperty("state_type") String stateType, // "checkin" o "checkout"
        @JsonProperty("gas_level") String gasLevel, // solo para checkin
        @JsonProperty("valuables") String valuables, // solo para checkin
        @JsonProperty("vehicle_diagnosis") String vehicleDiagnosis, // solo para checkout
        @JsonProperty("rating") Byte rating // solo para checkout
) {}