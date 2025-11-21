package com.mechanical_workshop_usm.image_module.dashboard_lights_image;

import com.mechanical_workshop_usm.image_module.image.dto.GetImage;
import com.mechanical_workshop_usm.image_module.image.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard-lights")
public class DashboardLightsController {

    ImageService imageService;
    public DashboardLightsController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<GetImage>> getImageCategory() {
        List<GetImage> response = imageService.findAllDashboardLights();
        return ResponseEntity.ok(response);
    }
}
