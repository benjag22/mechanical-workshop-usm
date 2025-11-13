package com.mechanical_workshop_usm.client_info_module;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ClientInfoValidator {
    private final ClientInfoRepository clientInfoRepository;
    public ClientInfoValidator(ClientInfoRepository clientInfoRepository) {
        this.clientInfoRepository = clientInfoRepository;
    }

    public void validateOnCreate(CreateClientRequest request){

        List< FieldErrorResponse> errors = new ArrayList<>();

        String firstName = request.firstName();
        String lastName = request.lastName();
        String emailAddress = request.emailAddress();
        String address = request.address();
        String cellphoneNumber = request.cellphoneNumber();
        String rut = request.rut();

        Pattern cellphonePattern = Pattern.compile("^\\+569\\d{8}$");
        Pattern emailAddressPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

        if(firstName.isEmpty()){
            errors.add(new FieldErrorResponse("first_name", "The first name cannot be empty"));
        }

        if(lastName.isEmpty()){
            errors.add(new FieldErrorResponse("last_name", "The last name cannot be empty"));
        }
        if(emailAddress.isEmpty()){
            errors.add(new FieldErrorResponse("email_address", "The email address cannot be empty"));
        }
        if (!emailAddressPattern.matcher(emailAddress).matches()) {
            errors.add(new FieldErrorResponse("email_address", "Invalid email address"));
        }
        if(address.isEmpty()){
            errors.add(new FieldErrorResponse("address", "Address is empty"));
        }

        if(cellphoneNumber.isEmpty()){
            errors.add(new FieldErrorResponse("cellphone_number", "The cellphone cannot be empty"));
        }
        if(!cellphonePattern.matcher(cellphoneNumber).matches()){
            errors.add(new FieldErrorResponse("cellphone_number", "Invalid cellphone number"));
        }
        if (rut == null || rut.trim().isEmpty()) {
            errors.add(new FieldErrorResponse("rut", "RUT cannot be empty"));
        } else if (!isValidRut(rut)) {
            errors.add(new FieldErrorResponse("rut", "Invalid RUT verification digit"));
        }

        if(!errors.isEmpty()){
            throw new MultiFieldException("Some error in fields", errors);
        }
    }

    public boolean isValidRut(String completeRUt) {
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
