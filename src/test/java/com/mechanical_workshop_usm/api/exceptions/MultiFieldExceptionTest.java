package com.mechanical_workshop_usm.api.exceptions;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiFieldExceptionTest {

    @Test
    void constructorShouldSetMessageAndErrors() {
        String mensaje = "Error de validación";
        List<FieldErrorResponse> errores = List.of(
                new FieldErrorResponse("email", "Formato inválido"),
                new FieldErrorResponse("password", "Campo requerido")
        );

        MultiFieldException ex = new MultiFieldException(mensaje, errores);

        assertEquals(mensaje, ex.getMessage());
        assertEquals(errores, ex.getErrors());
    }

    @Test
    void inheritsFromRuntimeException() {
        MultiFieldException ex = new MultiFieldException("msg", List.of());
        assertInstanceOf(RuntimeException.class, ex);
    }
}