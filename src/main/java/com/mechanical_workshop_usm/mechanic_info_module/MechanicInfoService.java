package com.mechanical_workshop_usm.mechanic_info_module;

import com.mechanical_workshop_usm.image_module.image.dto.CreateImageRequest;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicRequest;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicResponse;
import com.mechanical_workshop_usm.mechanic_info_module.dto.Mechanic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanicInfoService {
    private final MechanicInfoRepository mechanicInfoRepository;
    private final MechanicInfoValidator mechanicInfoValidator;

    public MechanicInfoService(MechanicInfoRepository mechanicInfoRepository, MechanicInfoValidator mechanicInfoValidator) {
        this.mechanicInfoRepository = mechanicInfoRepository;
        this.mechanicInfoValidator = mechanicInfoValidator;
    }
    public List<Mechanic> getAllMechanics() {
        return mechanicInfoRepository.findAll().stream()
            .map(m -> new Mechanic(m.getId(), m.getName(), m.getRut()))
            .toList();
    }
    public CreateMechanicResponse createMechanic(CreateMechanicRequest createMechanicRequest) {
        mechanicInfoValidator.validateOnCreate(createMechanicRequest);
        MechanicInfo mechanicInfo = MechanicInfo.builder()
            .rut(createMechanicRequest.rut())
            .name(createMechanicRequest.name())
            .build();

        MechanicInfo saved = mechanicInfoRepository.save(mechanicInfo);
        return new CreateMechanicResponse(saved.getId(), saved.getRut());
    }
}