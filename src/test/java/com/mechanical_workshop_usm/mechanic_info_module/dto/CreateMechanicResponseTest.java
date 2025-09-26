package com.mechanical_workshop_usm.mechanic_info_module.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateMechanicResponseTest {

    @Test
    void testRecordFields() {
        CreateMechanicResponse dto = new CreateMechanicResponse(42L, "MECH2025");

        assertEquals(42L, dto.id());
        assertEquals("MECH2025", dto.registrationNumber());
    }
}