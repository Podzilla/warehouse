package com.podzilla.warehouse.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PackagerTest {

    @Test
    void testDefaultConstructorAndSetters() {
        // Arrange
        Packager packager = new Packager();

        // Act
        packager.setId(1L);
        packager.setName("Default Constructor");
        packager.setActive(true);

        // Assert
        assertEquals(1L, packager.getId());
        assertEquals("Default Constructor", packager.getName());
        assertTrue(packager.isActive());
    }

    @Test
    void testConstructorWithName() {
        // Arrange & Act
        Packager packager = new Packager("NameOnly");

        // Assert
        assertNull(packager.getId());
        assertEquals("NameOnly", packager.getName());
        assertTrue(packager.isActive()); // default boolean value
    }

    @Test
    void testConstructorWithNameAndActive() {
        // Arrange & Act
        Packager packager = new Packager("FullConstructor", true);

        // Assert
        assertNull(packager.getId());
        assertEquals("FullConstructor", packager.getName());
        assertTrue(packager.isActive());
    }
}
