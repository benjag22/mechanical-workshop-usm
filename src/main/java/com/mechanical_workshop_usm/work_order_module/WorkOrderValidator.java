package com.mechanical_workshop_usm.work_order_module;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfoRepository;
import com.mechanical_workshop_usm.work_service_module.WorkServiceRepository;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkOrderValidator {

    private final WorkServiceRepository workServiceRepository;
    private final MechanicInfoRepository mechanicInfoRepository;

    public WorkOrderValidator(
            WorkServiceRepository workServiceRepository,
            MechanicInfoRepository mechanicInfoRepository
    ) {
        this.workServiceRepository = workServiceRepository;
        this.mechanicInfoRepository = mechanicInfoRepository;
    }


    public void validateOnCreate(CreateWorkOrderRequest request, Integer carPicturesLength, Boolean isSignature) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        if (Boolean.FALSE.equals(isSignature)) {
            errors.add(new FieldErrorResponse("signature", "Mechanic's signature is required"));
        }

        if (carPicturesLength != null && carPicturesLength > 10) {
            errors.add(new FieldErrorResponse("carPictures", "You cannot send more than ten photos"));
        }

        boolean hasExistingServices = request.serviceIds() != null && !request.serviceIds().isEmpty();
        boolean hasNewServices = request.newServices() != null && !request.newServices().isEmpty();
        if (!hasExistingServices && !hasNewServices) {
            errors.add(new FieldErrorResponse("services", "At least one existing service id or one new service is required"));
        }

        if (hasExistingServices) {
            int idx = 0;
            for (Integer sid : request.serviceIds()) {
                if (sid == null) {
                    errors.add(new FieldErrorResponse("serviceIds[" + idx + "]", "Service id is required"));
                } else if (!workServiceRepository.existsById(sid)) {
                    errors.add(new FieldErrorResponse("serviceIds[" + idx + "]", "Service not found: " + sid));
                }
                idx++;
            }
        }

        boolean hasMechanics = request.mechanicIds() != null && !request.mechanicIds().isEmpty();
        if (!hasMechanics) {
            errors.add(new FieldErrorResponse("mechanicIds", "At least one mechanic id is required"));
        } else {
            int idx = 0;
            for (Integer mid : request.mechanicIds()) {
                if (mid == null) {
                    errors.add(new FieldErrorResponse("mechanicIds[" + idx + "]", "Mechanic id is required"));
                } else if (!mechanicInfoRepository.existsById(mid)) {
                    errors.add(new FieldErrorResponse("mechanicIds[" + idx + "]", "Mechanic not found: " + mid));
                }
                idx++;
            }
        }

        Integer leaderId = request.leaderMechanicId();
        if (leaderId == null) {
            errors.add(new FieldErrorResponse("leaderMechanicId", "Leader mechanic id is required"));
        } else {
            if (!mechanicInfoRepository.existsById(leaderId)) {
                errors.add(new FieldErrorResponse("leaderMechanicId", "Leader mechanic not found: " + leaderId));
            }
            if (hasMechanics && !request.mechanicIds().contains(leaderId)) {
                errors.add(new FieldErrorResponse("leaderMechanicId", "Leader mechanic id must be included in mechanicIds"));
            }
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Invalid work order create request", errors);
        }
    }
}