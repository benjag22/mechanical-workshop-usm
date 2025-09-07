package com.mechanical_workshop_usm.api.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FieldErrorResponseTest {

    @Test
    void constructorAndAccessorsShouldWork() {
        String field = "email";
        String message = "Formato inválido";

        FieldErrorResponse error = new FieldErrorResponse(field, message);

        assertEquals(field, error.field());
        assertEquals(message, error.message());
    }

    @Test
    void equalsAndHashCodeShouldWork() {
        FieldErrorResponse error1 = new FieldErrorResponse("username", "Requerido");
        FieldErrorResponse error2 = new FieldErrorResponse("username", "Requerido");
        FieldErrorResponse error3 = new FieldErrorResponse("password", "Requerido");

        assertEquals(error1, error2);
        assertEquals(error1.hashCode(), error2.hashCode());
        assertNotEquals(error1, error3);
    }

    @Test
    void toStringShouldContainFieldAndMessage() {
        FieldErrorResponse error = new FieldErrorResponse("telefono", "Debe ser numérico");
        String str = error.toString();

        assertTrue(str.contains("telefono"));
        assertTrue(str.contains("Debe ser numérico"));
    }
}