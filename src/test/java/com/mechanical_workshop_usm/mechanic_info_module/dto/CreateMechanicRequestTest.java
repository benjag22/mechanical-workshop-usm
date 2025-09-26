package com.mechanical_workshop_usm.mechanic_info_module.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateMechanicRequestTest {

    @Test
    void testRecordFields() {
        CreateMechanicRequest dto = new CreateMechanicRequest("Ana", "Gómez", "REG2023");

        assertEquals("Ana", dto.firstName());
        assertEquals("Gómez", dto.lastName());
        assertEquals("REG2023", dto.registrationNumber());
    }
}