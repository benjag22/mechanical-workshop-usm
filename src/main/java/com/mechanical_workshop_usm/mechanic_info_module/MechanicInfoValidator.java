package com.mechanical_workshop_usm.mechanic_info_module;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.mechanic_info_module.dtos.CreateMechanicRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MechanicInfoValidator {
    public void validateOnCreate(CreateMechanicRequest request) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        if (request.firstName() == null || request.firstName().isEmpty()) {
            errors.add(new FieldErrorResponse("first_name", "The first name cannot be empty"));
        }

        if (request.registrationNumber() == null || request.registrationNumber().isEmpty()) {
            errors.add(new FieldErrorResponse("registration_number", "The registration number cannot be empty"));
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Some error in fields", errors);
        }
    }
}