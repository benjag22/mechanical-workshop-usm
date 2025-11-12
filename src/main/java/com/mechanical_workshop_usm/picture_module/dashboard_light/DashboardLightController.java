package com.mechanical_workshop_usm.picture_module.dashboard_light;

import com.mechanical_workshop_usm.picture_module.picture.dto.CreatePictureResponse;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreatePictureResponse> create(
            @RequestPart("image") MultipartFile image,
            @RequestPart(value = "alt", required = false) String alt
    ) {
        CreatePictureResponse resp = service.createFromMultipart(image, alt);
        return ResponseEntity.ok(resp);
    }

    @GetMapping
    public ResponseEntity<List<CreatePictureResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreatePictureResponse> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}