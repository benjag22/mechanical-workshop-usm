package com.mechanical_workshop_usm.car_module.car.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetCarFullResponse(
        int id,
        @JsonProperty("vin") String VIN,
        @JsonProperty("license_plate") String licensePlate,
        @JsonProperty("model_id") int modelId,
        @JsonProperty("model_name") String modelName,
        @JsonProperty("model_type") String modelType,
        @JsonProperty("model_year") int modelYear,
        @JsonProperty("brand_id") int brandId,
        @JsonProperty("brand_name") String brandName
) {}