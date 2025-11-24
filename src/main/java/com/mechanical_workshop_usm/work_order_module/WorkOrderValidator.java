package com.mechanical_workshop_usm.work_order_module;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfoRepository;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfoService;
import com.mechanical_workshop_usm.work_service_module.WorkServiceRepository;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkOrderValidator {

    private final WorkServiceRepository workServiceRepository;
    private final MechanicInfoRepository mechanicInfoRepository;
    private final MechanicInfoService mechanicInfoService;

    public WorkOrderValidator(
            WorkServiceRepository workServiceRepository,
            MechanicInfoRepository mechanicInfoRepository,
            MechanicInfoService mechanicInfoService
            ) {
        this.workServiceRepository = workServiceRepository;
        this.mechanicInfoRepository = mechanicInfoRepository;
        this.mechanicInfoService = mechanicInfoService;
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

        if (hasNewServices) {
            int idx = 0;
            for (var ns : request.newServices()) {
                if (ns.serviceName() == null || ns.serviceName().isBlank()) {
                    errors.add(new FieldErrorResponse("newServices[" + idx + "].serviceName",
                            "Service name is required"));
                } else if (workServiceRepository.existsByServiceName(ns.serviceName())) {
                    errors.add(new FieldErrorResponse("newServices[" + idx + "].serviceName",
                            "A service with this name already exists: " + ns.serviceName()));
                }
                idx++;
            }
        }

        if (hasNewServices) {
            int idx = 0;
            for (var ns : request.newServices()) {
                String time = ns.estimatedTime();
                if (time == null || time.isBlank()) {
                    errors.add(new FieldErrorResponse("newServices[" + idx + "].estimatedTime",
                        "Estimated time is required"));
                }
                idx++;
            }
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
        boolean hasNewMechanics = request.newMechanics() != null && !request.newMechanics().isEmpty();
        boolean hasLeaderId = request.leaderMechanicId() != null;
        boolean hasNewLeaderMechanic = request.newLeaderMechanic() != null;

        if (!hasMechanics && !hasNewMechanics && !hasLeaderId && !hasNewLeaderMechanic) {
            errors.add(new FieldErrorResponse(
                    "mechanics",
                    "You must provide at least one mechanic id or one new mechanic"
            ));
        }

        if (hasMechanics) {
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

        if (hasNewMechanics) {
            int idx = 0;
            for (var nm : request.newMechanics()) {

                if (nm.rut() == null || nm.rut().isBlank()) {
                    errors.add(new FieldErrorResponse(
                            "newMechanics[" + idx + "].rut",
                            "Rut is required"
                    ));
                } else if (mechanicInfoService.isRutValid(nm.rut())) {
                    errors.add(new FieldErrorResponse(
                            "newMechanics[" + idx + "].rut",
                            "Invalid rut format"
                    ));
                }
                idx++;
            }
        }

        if (hasLeaderId && hasNewLeaderMechanic) {
            errors.add(new FieldErrorResponse(
                    "leaderMechanic",
                    "You cannot provide both leaderMechanicId and newLeaderMechanic"
            ));
        }
;
        if (!hasLeaderId && !hasNewLeaderMechanic) {
            errors.add(new FieldErrorResponse(
                    "leaderMechanic",
                    "You must provide either leaderMechanicId or newLeaderMechanic"
            ));
        }

        if (hasLeaderId) {
            Integer leaderId = request.leaderMechanicId();

            if (!mechanicInfoRepository.existsById(leaderId)) {
                errors.add(new FieldErrorResponse("leaderMechanicId", "Leader mechanic not found: " + leaderId));
            }

            if (hasMechanics && request.mechanicIds().contains(leaderId)) {
                errors.add(new FieldErrorResponse(
                        "leaderMechanicId",
                        "Leader mechanic id must NOT be inside mechanicIds list"
                ));
            }
        }

        if (hasNewLeaderMechanic) {
            var nl = request.newLeaderMechanic();

            if (nl.rut() == null || nl.rut().isBlank()) {
                errors.add(new FieldErrorResponse(
                        "newLeaderMechanic.rut",
                        "Rut is required"
                ));
            } else if (mechanicInfoService.isRutValid(nl.rut())) {
                errors.add(new FieldErrorResponse(
                        "newLeaderMechanic.rut",
                        "Invalid rut format"
                ));
            }
        }

        if (hasNewMechanics) {
            int idx = 0;
            for (var nm : request.newMechanics()) {

                if (nm.rut() != null && !nm.rut().isBlank()) {

                    if (mechanicInfoRepository.existsByRut(nm.rut())) {
                        errors.add(new FieldErrorResponse(
                                "newMechanics[" + idx + "].rut",
                                "This rut is already registered: " + nm.rut()
                        ));
                    }
                }

                idx++;
            }
        }

        if (hasNewLeaderMechanic) {
            var nl = request.newLeaderMechanic();

            if (nl.rut() != null && !nl.rut().isBlank()) {

                if (mechanicInfoRepository.existsByRut(nl.rut())) {
                    errors.add(new FieldErrorResponse(
                            "newLeaderMechanic.rut",
                            "This rut is already registered: " + nl.rut()
                    ));
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Invalid work order create request", errors);
        }
    }
}