package com.mechanical_workshop_usm.mechanic_info_module;

import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicRequest;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicResponse;
import org.springframework.stereotype.Service;

@Service
public class MechanicInfoService {
    private final MechanicInfoRepository mechanicInfoRepository;
    private final MechanicInfoValidator mechanicInfoValidator;

    public MechanicInfoService(MechanicInfoRepository mechanicInfoRepository, MechanicInfoValidator mechanicInfoValidator) {
        this.mechanicInfoRepository = mechanicInfoRepository;
        this.mechanicInfoValidator = mechanicInfoValidator;
    }

    public CreateMechanicResponse createMechanic(CreateMechanicRequest createMechanicRequest) {
        mechanicInfoValidator.validateOnCreate(createMechanicRequest);
        MechanicInfo mechanicInfo = new MechanicInfo(
                createMechanicRequest.firstName(),
                createMechanicRequest.lastName(),
                createMechanicRequest.registrationNumber()
        );
        MechanicInfo saved = mechanicInfoRepository.save(mechanicInfo);
        return new CreateMechanicResponse(saved.getId(), saved.getRegistrationNumber());
    }
}