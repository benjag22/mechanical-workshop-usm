package com.mechanical_workshop_usm.mechanic_info_module;

import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicRequest;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mechanic")
public class MechanicInfoController {
    private final MechanicInfoService mechanicInfoService;
    private final MechanicInfoRepository mechanicInfoRepository;

    public MechanicInfoController(MechanicInfoService mechanicInfoService, MechanicInfoRepository mechanicInfoRepository) {
        this.mechanicInfoService = mechanicInfoService;
        this.mechanicInfoRepository = mechanicInfoRepository;
    }

    @GetMapping
    public List<MechanicInfo> getAllMechanics() {
        return mechanicInfoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<CreateMechanicResponse> createMechanic(@RequestBody CreateMechanicRequest request) {
        CreateMechanicResponse response = mechanicInfoService.createMechanic(request);
        return ResponseEntity.ok(response);
    }
}