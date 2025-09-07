package com.mechanical_workshop_usm.mechanic_info_module;

import com.mechanical_workshop_usm.mechanic_info_module.dtos.CreateMechanicRequest;
import com.mechanical_workshop_usm.mechanic_info_module.dtos.CreateMechanicResponse;
import org.springframework.stereotype.Service;

@Service
public class MechanicInfoService {
    private final MechanicInfoRepository mechanicInfoRepository;
    private final MechanicInfoValidator mechanicInfoValidator;

    public MechanicInfoService(MechanicInfoRepository mechanicInfoRepository, MechanicInfoValidator mechanicInfoValidator) {
        this.mechanicInfoRepository = mechanicInfoRepository;
        this.mechanicInfoValidator = mechanicInfoValidator;
    }

    public CreateMechanicResponse createMechanic(CreateMechanicRequest request) {
        mechanicInfoValidator.validateOnCreate(request);
        MechanicInfo mechanicInfo = new MechanicInfo();
        mechanicInfo.setFirstName(request.firstName());
        mechanicInfo.setLastName(request.lastName());
        mechanicInfo.setRegistrationNumber(request.registrationNumber());
        MechanicInfo saved = mechanicInfoRepository.save(mechanicInfo);
        return new CreateMechanicResponse(saved.getId(), saved.getRegistrationNumber());
    }
}