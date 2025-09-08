package com.mechanical_workshop_usm.mechanic_info_module;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MechanicInfoTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Usando el constructor vacío y los setters (sin considerar id)
        MechanicInfo mechanic = new MechanicInfo();
        mechanic.setFirstName("Juan");
        mechanic.setLastName("Pérez");
        mechanic.setRegistrationNumber("MECH2025");

        assertEquals("Juan", mechanic.getFirstName());
        assertEquals("Pérez", mechanic.getLastName());
        assertEquals("MECH2025", mechanic.getRegistrationNumber());
        assertEquals(0, mechanic.getId());
    }

    @Test
    void testNoArgsConstructorAndSetters_NullLastName() {
        // Caso con lastName = null
        MechanicInfo mechanic = new MechanicInfo();
        mechanic.setFirstName("Ana");
        mechanic.setLastName(null);
        mechanic.setRegistrationNumber("MECH2026");

        assertEquals("Ana", mechanic.getFirstName());
        assertNull(mechanic.getLastName());
        assertEquals("MECH2026", mechanic.getRegistrationNumber());
        assertEquals(0, mechanic.getId());
    }

    @Test
    void testNoArgsConstructorAndSetters_EmptyLastName() {
        // Caso con lastName = ""
        MechanicInfo mechanic = new MechanicInfo();
        mechanic.setFirstName("Pedro");
        mechanic.setLastName("");
        mechanic.setRegistrationNumber("MECH2027");

        assertEquals("Pedro", mechanic.getFirstName());
        assertEquals("", mechanic.getLastName());
        assertEquals("MECH2027", mechanic.getRegistrationNumber());
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