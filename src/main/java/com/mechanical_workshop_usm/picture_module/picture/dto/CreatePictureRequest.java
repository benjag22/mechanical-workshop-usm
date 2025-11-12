package com.mechanical_workshop_usm.picture_module.picture.dto;


public record CreatePictureRequest(
        String alt,
        String fileName,
        String path
){}