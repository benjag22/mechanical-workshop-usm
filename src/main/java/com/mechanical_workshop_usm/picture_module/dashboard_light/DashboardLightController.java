package com.mechanical_workshop_usm.picture_module.dashboard_light;

import com.mechanical_workshop_usm.picture_module.picture.dto.GetPictureResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard-lights")
public class DashboardLightController {

    private final DashboardLightService service;

    public DashboardLightController(DashboardLightService service) {
        this.service = service;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Upload a dashboard light image",
            description = "Uploads an image and stores dashboard light information."
    )
    public ResponseEntity<GetPictureResponse> create(
            @RequestPart("image")
            @Parameter(description = "Image file to upload")
            MultipartFile image,

            @RequestPart("alt")
            @Parameter(description = "Dashboard light metadata in JSON format")
            String alt
    ) {
        GetPictureResponse resp = service.createFromMultipart(image, alt);
        return ResponseEntity.ok(resp);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get all dashboard light images",
            description = "Returns a list of dashboard light images with metadata and public URLs."
    )
    public ResponseEntity<List<GetPictureResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get a dashboard light by id",
            description = "Returns metadata and public URL for the dashboard light identified by the given id."
    )
    public ResponseEntity<GetPictureResponse> get(
            @PathVariable
            @Parameter(description = "ID of the dashboard light to retrieve", required = true, example = "1")
            Integer id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }
}