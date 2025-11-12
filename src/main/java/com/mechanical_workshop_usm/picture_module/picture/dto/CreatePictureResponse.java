package com.mechanical_workshop_usm.picture_module.picture.dto;

public record CreatePictureResponse(
        Integer id,
        String alt,
        String path
) {}