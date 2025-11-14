package com.mechanical_workshop_usm.picture_module.picture;


import com.mechanical_workshop_usm.picture_module.picture.dto.GetPictureResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/picture")
public class PictureController {

    private final PictureService service;

    public PictureController(PictureService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GetPictureResponse> create(
            @RequestPart("image") MultipartFile image,
            @RequestPart(value = "alt", required = false) String alt
    ) {
        GetPictureResponse resp = service.createFromMultipart(image, alt);
        return ResponseEntity.ok(resp);
    }

    @GetMapping
    public ResponseEntity<List<GetPictureResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPictureResponse> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}