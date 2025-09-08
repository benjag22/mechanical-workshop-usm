package com.mechanical_workshop_usm.mechanic_info_module;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MechanicInfoTest {

    @Test
    void testConstructorsAndGettersSetters() {
        // Haciendo uso del constructor vacio y seteando despues
        MechanicInfo mechanic = new MechanicInfo();
        mechanic.setFirstName("Juan");
        mechanic.setLastName("Pérez");
        mechanic.setRegistrationNumber("MECH2025");

        assertEquals("Juan", mechanic.getFirstName());
        assertEquals("Pérez", mechanic.getLastName());
        assertEquals("MECH2025", mechanic.getRegistrationNumber());

        // Haciendo uso del contructor perzonalizado
        MechanicInfo mechanic2 = new MechanicInfo("Ana", "Gómez", "MECH1234");
        assertEquals(0, mechanic2.getId());
        assertEquals("Ana", mechanic2.getFirstName());
        assertEquals("Gómez", mechanic2.getLastName());
        assertEquals("MECH1234", mechanic2.getRegistrationNumber());
    }

    @Test
    void testNoArgsAndCustomConstructor() {
        // Prueba de creacion de constructor vacio
        MechanicInfo mechanic = new MechanicInfo();
        assertNotNull(mechanic);

        // Constructor sin id
        MechanicInfo mechanic2 = new MechanicInfo("Pedro", "López", "REG001");
        assertNotNull(mechanic2);
        assertEquals(0, mechanic2.getId());
        assertEquals("Pedro", mechanic2.getFirstName());
        assertEquals("López", mechanic2.getLastName());
        assertEquals("REG001", mechanic2.getRegistrationNumber());
    }
}