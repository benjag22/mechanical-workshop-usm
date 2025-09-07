package com.mechanical_workshop_usm.mechanic_info_module;

import com.mechanical_workshop_usm.mechanic_info_module.dtos.CreateMechanicRequest;
import com.mechanical_workshop_usm.mechanic_info_module.dtos.CreateMechanicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicInfoController {
    private final MechanicInfoService mechanicInfoService;
    private final MechanicInfoRepository repository;

    public MechanicInfoController(MechanicInfoService mechanicInfoService, MechanicInfoRepository repository) {
        this.mechanicInfoService = mechanicInfoService;
        this.repository = repository;
    }

    @GetMapping
    public List<MechanicInfo> getAllMechanics() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<CreateMechanicResponse> createMechanic(@RequestBody CreateMechanicRequest request) {
        CreateMechanicResponse response = mechanicInfoService.createMechanic(request);
        return ResponseEntity.ok(response);
    }
}