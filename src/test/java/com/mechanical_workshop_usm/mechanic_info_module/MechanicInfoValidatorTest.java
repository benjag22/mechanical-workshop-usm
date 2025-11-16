package com.mechanical_workshop_usm.mechanic_info_module;

import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MechanicInfoValidatorTest {

    private final MechanicInfoValidator validator = new MechanicInfoValidator();

    /*@Test
    void validRequestDoesNotThrow() {
        CreateMechanicRequest req = new CreateMechanicRequest("Ana", "Gómez", "REG2023");
        assertDoesNotThrow(() -> validator.validateOnCreate(req));
    }

    @Test
    void nullFirstNameThrows() {
        CreateMechanicRequest req = new CreateMechanicRequest(null, "Gómez", "REG2023");
        MultiFieldException ex = assertThrows(MultiFieldException.class, () -> validator.validateOnCreate(req));
        assertTrue(ex.getErrors().stream().anyMatch(e -> e.field().equals("first_name")));
    }

    @Test
    void emptyFirstNameThrows() {
        CreateMechanicRequest req = new CreateMechanicRequest("", "Gómez", "REG2023");
        MultiFieldException ex = assertThrows(MultiFieldException.class, () -> validator.validateOnCreate(req));
        assertTrue(ex.getErrors().stream().anyMatch(e -> e.field().equals("first_name")));
    }

    @Test
    void nullRegistrationNumberThrows() {
        CreateMechanicRequest req = new CreateMechanicRequest("Ana", "Gómez", null);
        MultiFieldException ex = assertThrows(MultiFieldException.class, () -> validator.validateOnCreate(req));
        assertTrue(ex.getErrors().stream().anyMatch(e -> e.field().equals("registration_number")));
    }

    @Test
    void emptyRegistrationNumberThrows() {
        CreateMechanicRequest req = new CreateMechanicRequest("Ana", "Gómez", "");
        MultiFieldException ex = assertThrows(MultiFieldException.class, () -> validator.validateOnCreate(req));
        assertTrue(ex.getErrors().stream().anyMatch(e -> e.field().equals("registration_number")));
    }

    @Test
    void bothFieldsInvalidThrowsBothErrors() {
        CreateMechanicRequest req = new CreateMechanicRequest("", "Gómez", "");
        MultiFieldException ex = assertThrows(MultiFieldException.class, () -> validator.validateOnCreate(req));
        assertEquals(2, ex.getErrors().size());
        assertTrue(ex.getErrors().stream().anyMatch(e -> e.field().equals("first_name")));
        assertTrue(ex.getErrors().stream().anyMatch(e -> e.field().equals("registration_number")));
    }*/
}