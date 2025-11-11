package com.mechanical_workshop_usm.record_module.record_state.dto.check_in;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mechanical_workshop_usm.car_module.car.dto.CreateCarRequest;
import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandRequest;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelRequest;
import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientRequest;
import com.mechanical_workshop_usm.record_module.record_state.dto.record_state.CreateRecordStateRequest;
import com.mechanical_workshop_usm.tool_module.dto.CreateToolRequest;

import java.util.List;

public record CreateCheckInRequest(
        @JsonProperty("client_id") Integer clientId,
        @JsonProperty("client") CreateClientRequest client,

        @JsonProperty("car_id") Integer carId,
        @JsonProperty("car") CreateCarRequest car,
        @JsonProperty("car_model") CreateCarModelRequest car_model,
        @JsonProperty("car_brand") CreateCarBrandRequest car_brand,

        @JsonProperty("mechanical_conditions_ids") List<Integer> mechanicalConditionsIds,

        @JsonProperty("tools_ids") List<Integer> toolsIds,
        @JsonProperty("tools") List<CreateToolRequest> tools,

        @JsonProperty("reason") String reason,

        @JsonProperty("record_state") CreateRecordStateRequest recordState,

        @JsonProperty("gas_level") String gasLevel,
        @JsonProperty("valuables") String valuables
) {}