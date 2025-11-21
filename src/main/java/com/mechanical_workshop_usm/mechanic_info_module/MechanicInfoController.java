package com.mechanical_workshop_usm.mechanic_info_module;

import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicRequest;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicResponse;
import com.mechanical_workshop_usm.mechanic_info_module.dto.GetMechanicInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mechanic")
public class MechanicInfoController {
    private final MechanicInfoService mechanicInfoService;

    public MechanicInfoController(MechanicInfoService mechanicInfoService) {
        this.mechanicInfoService = mechanicInfoService;
    }

    @GetMapping
    public ResponseEntity<List<GetMechanicInfo>> getAllMechanics() {
        List<GetMechanicInfo> response = mechanicInfoService.getAllMechanics();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateMechanicResponse> createMechanic(@RequestBody CreateMechanicRequest request) {
        CreateMechanicResponse response = mechanicInfoService.createMechanic(request);
        return ResponseEntity.ok(response);
    }
}