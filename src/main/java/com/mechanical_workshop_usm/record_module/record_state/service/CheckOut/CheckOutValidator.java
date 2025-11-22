package com.mechanical_workshop_usm.record_module.record_state.service.CheckOut;


import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfoRepository;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfoService;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_out.CreateCheckOutRequest;
import com.mechanical_workshop_usm.work_service_module.WorkServiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckOutValidator {


    public CheckOutValidator(
    ) {
    }

    public void validateOnCreate(CreateCheckOutRequest request) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        if (request.mileage() < 0)
            errors.add(new FieldErrorResponse("mileage", "El kilometraje no puede ser negativo"));

        if (request.vehicleDiagnosis() == null || request.vehicleDiagnosis().isBlank())
            errors.add(new FieldErrorResponse("vehicleDiagnosis", "El diagnóstico del vehículo es obligatorio"));

        if (!errors.isEmpty())
            throw new MultiFieldException("CheckOut inválido", errors);
    }
}
