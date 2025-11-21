package com.mechanical_workshop_usm.mechanic_info_module;

import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicRequest;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicResponse;
import com.mechanical_workshop_usm.mechanic_info_module.dto.GetMechanicInfo;
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
    public List<GetMechanicInfo> getAllMechanics() {
        return mechanicInfoRepository.findAll().stream()
            .map(m -> new GetMechanicInfo(m.getId(), m.getName(), m.getRut()))
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

    public boolean isRutValid(String rut) {
        if (rut == null) return true;

        String normalized = rut.replace(".", "").replace("-", "").trim().toUpperCase();
        if (normalized.length() < 2) return true;

        char dvChar = normalized.charAt(normalized.length() - 1);
        String numberPart = normalized.substring(0, normalized.length() - 1);

        for (int i = 0; i < numberPart.length(); i++) {
            if (!Character.isDigit(numberPart.charAt(i))) return true;
        }

        int sum = 0;
        int multiplier = 2;
        for (int i = numberPart.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(numberPart.charAt(i));
            sum += digit * multiplier;
            multiplier = (multiplier == 7) ? 2 : multiplier + 1;
        }

        int remainder = sum % 11;
        int computed = 11 - remainder;

        char expectedDv;
        if (computed == 11) {
            expectedDv = '0';
        } else if (computed == 10) {
            expectedDv = 'K';
        } else {
            expectedDv = (char) ('0' + computed);
        }

        return expectedDv != dvChar;
    }
    public MechanicInfo getMechanicById(int id) {
        return mechanicInfoRepository.getByIdOrThrow(id);
    }
}