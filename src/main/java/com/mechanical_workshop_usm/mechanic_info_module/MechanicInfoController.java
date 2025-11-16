package com.mechanical_workshop_usm.mechanic_info_module;

import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicRequest;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicResponse;
import com.mechanical_workshop_usm.mechanic_info_module.dto.Mechanic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/mechanic")
public class MechanicInfoController {
    private final MechanicInfoService mechanicInfoService;

    public MechanicInfoController(MechanicInfoService mechanicInfoService) {
        this.mechanicInfoService = mechanicInfoService;
    }

    @GetMapping
    public ResponseEntity<List<Mechanic>> getAllMechanics() {
        List<Mechanic> response = mechanicInfoService.getAllMechanics();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateMechanicResponse> createMechanic(@RequestBody CreateMechanicRequest request) {
        CreateMechanicResponse response = mechanicInfoService.createMechanic(request);
        return ResponseEntity.ok(response);
    }
}