package com.mechanical_workshop_usm.record_module.record.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateRecordResponse(
        @JsonProperty("id") int id,
        @JsonProperty("reason") String reason,
        @JsonProperty("car_id") int carId,
        @JsonProperty("client_info_id") long clientInfoId,
        @JsonProperty("mechanic_info_id") int mechanicInfoId
) {}