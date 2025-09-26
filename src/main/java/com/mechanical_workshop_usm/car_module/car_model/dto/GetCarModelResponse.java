package com.mechanical_workshop_usm.car_module.car_model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetCarModelResponse(
        long id,
        @JsonProperty("model_name") String modelName,
        @JsonProperty("model_type") String modelType,
        @JsonProperty("model_year") int modelYear,
        @JsonProperty("brand_id") long brandId,
        @JsonProperty("brand_name") String brandName
) {}