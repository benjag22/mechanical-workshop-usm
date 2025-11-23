package com.mechanical_workshop_usm.record_module.record_state.service.CheckOut;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_out.CreateCheckOutRequest;
import com.mechanical_workshop_usm.record_module.record_state.persistence.repository.CheckOutRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CheckOutValidator {

    private final CheckOutRepository repository;

    public CheckOutValidator(CheckOutRepository repository) {
        this.repository = repository;
    }

    public void validateOnCreate(CreateCheckOutRequest request, int recordId) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        if (request.mileage() < 0) {
            errors.add(new FieldErrorResponse("mileage", "El kilometraje no puede ser negativo"));
        }

        if (request.vehicleDiagnosis() == null || request.vehicleDiagnosis().isBlank()) {
            errors.add(new FieldErrorResponse("vehicleDiagnosis", "El diagnóstico del vehículo es obligatorio"));
        }

        if (repository.existsByRecord_Id(recordId)) {
            errors.add(new FieldErrorResponse("workOrderId", "Checkout has already been completed for this registration"));
        }

        if (!errors.isEmpty())
            throw new MultiFieldException("CheckOut inválido", errors);
    }
}
