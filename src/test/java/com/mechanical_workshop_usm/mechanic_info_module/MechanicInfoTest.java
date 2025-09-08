package com.mechanical_workshop_usm.mechanic_info_module;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MechanicInfoTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Usando el constructor vacío y los setters (sin considerar id)
        MechanicInfo mechanic = new MechanicInfo();
        assertNotNull(mechanic);

        mechanic.setFirstName("Juan");
        mechanic.setLastName("Pérez");
        mechanic.setRegistrationNumber("MECH2025");

        assertEquals("Juan", mechanic.getFirstName());
        assertEquals("Pérez", mechanic.getLastName());
        assertEquals("MECH2025", mechanic.getRegistrationNumber());
        assertEquals(0, mechanic.getId());
    }

    @Test
    void testCustomConstructorWithoutId() {
        // Usando el constructor personalizado sin id
        MechanicInfo mechanic = new MechanicInfo("Ana", "Gómez", "MECH1234");
        assertNotNull(mechanic);

        assertEquals("Ana", mechanic.getFirstName());
        assertEquals("Gómez", mechanic.getLastName());
        assertEquals("MECH1234", mechanic.getRegistrationNumber());
        assertEquals(0, mechanic.getId());
    }
}