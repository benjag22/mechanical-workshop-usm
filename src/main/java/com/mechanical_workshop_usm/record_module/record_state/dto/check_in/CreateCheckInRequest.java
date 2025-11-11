package com.mechanical_workshop_usm.record_module.record_state.dto.check_in;

import com.mechanical_workshop_usm.car_module.car.dto.CreateCarRequest;
import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandRequest;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelRequest;
import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientRequest;
import com.mechanical_workshop_usm.record_module.record_state.dto.record_state.CreateRecordStateRequest;
import com.mechanical_workshop_usm.tool_module.dto.CreateToolRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CreateCheckInRequest(
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Integer clientId,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        CreateClientRequest client,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Integer carId,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        CreateCarRequest car,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        CreateCarModelRequest carModel,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        CreateCarBrandRequest carBrand,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        List<Integer> mechanicalConditionsIds,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        List<Integer> toolsIds,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        List<CreateToolRequest> newTools,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String reason,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        CreateRecordStateRequest recordState,

        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String gasLevel,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String valuables
) {}