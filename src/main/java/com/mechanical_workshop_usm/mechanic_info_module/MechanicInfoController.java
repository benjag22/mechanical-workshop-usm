package com.mechanical_workshop_usm.mechanic_info_module;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicInfoController {
    private final MechanicInfoRepository repository;

    public MechanicInfoController(MechanicInfoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<MechanicInfo> getAllMechanics() {
        return repository.findAll();
    }

    @PostMapping
    public MechanicInfo createMechanic(@RequestBody MechanicInfo mechanic) {
        return repository.save(mechanic);
    }
}