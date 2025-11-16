package com.mechanical_workshop_usm.mechanic_info_module;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MechanicInfoValidator {
    public void validateOnCreate(CreateMechanicRequest request) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        if (request.name() == null || request.name().isEmpty()) {
            errors.add(new FieldErrorResponse("name", "The name cannot be empty"));
        }

        if (request.rut() == null || request.rut().isEmpty()) {
            errors.add(new FieldErrorResponse("rut", "The registration number cannot be empty"));
            if(!isValidRut(request.rut())) {
                errors.add(new FieldErrorResponse("rut", "The rut number is invalid"));
            }
        }

        if (!errors.isEmpty()) {
            throw new MultiFieldException("Some error in fields", errors);
        }
    }

    private boolean isValidRut(String completeRUt) {
        if (completeRUt == null || !completeRUt.matches("^\\d{7,8}-[\\dkK]$")) {
            return false;
        }

        String[] parts = completeRUt.split("-");
        int rut;
        try {
            rut = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            return false;
        }

        char dv = Character.toUpperCase(parts[1].charAt(0));
        int m = 0, s = 1;
        while (rut != 0) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
            rut /= 10;
        }

        char dvCalculated = (s != 0) ? (char) (s + 47) : 'K';
        return dv == dvCalculated;
    }
}