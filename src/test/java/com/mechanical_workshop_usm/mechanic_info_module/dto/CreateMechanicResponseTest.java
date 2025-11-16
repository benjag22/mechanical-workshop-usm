package com.mechanical_workshop_usm.mechanic_info_module.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateMechanicResponseTest {

    @Test
    void testRecordFields() {
        CreateMechanicResponse dto = new CreateMechanicResponse(1, "MECH2025");

        assertEquals(1, dto.id());
        assertEquals("MECH2025", dto.rut());
    }
}