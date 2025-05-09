package com.podzilla.warehouse.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTests {

    @Test
    public void testManagerCreation_WithAllFields() {
        // Arrange & Act
        Manager manager = new Manager("John Doe", "john@example.com", "Warehouse", "123-456-7890");

        // Assert
        assertEquals("John Doe", manager.getName());
        assertEquals("john@example.com", manager.getEmail());
        assertEquals("Warehouse", manager.getDepartment());
        assertEquals("123-456-7890", manager.getPhoneNumber());
        assertTrue(manager.getIsActive()); // Default should be true
    }

    @Test
    public void testManagerCreation_WithoutPhone() {
        // Arrange & Act
        Manager manager = new Manager("John Doe", "john@example.com", "Warehouse");

        // Assert
        assertEquals("John Doe", manager.getName());
        assertEquals("john@example.com", manager.getEmail());
        assertEquals("Warehouse", manager.getDepartment());
        assertNull(manager.getPhoneNumber());
        assertTrue(manager.getIsActive()); // Default should be true
    }

    @Test
    public void testSetters() {
        // Arrange
        Manager manager = new Manager();

        // Act
        manager.setId(1L);
        manager.setName("Jane Smith");
        manager.setEmail("jane@example.com");
        manager.setDepartment("Logistics");
        manager.setPhoneNumber("987-654-3210");
        manager.setIsActive(false);

        // Assert
        assertEquals(1L, manager.getId());
        assertEquals("Jane Smith", manager.getName());
        assertEquals("jane@example.com", manager.getEmail());
        assertEquals("Logistics", manager.getDepartment());
        assertEquals("987-654-3210", manager.getPhoneNumber());
        assertFalse(manager.getIsActive());
    }

    @Test
    public void testDefaultConstructor() {
        // Arrange & Act
        Manager manager = new Manager();

        // Assert
        assertNull(manager.getId());
        assertNull(manager.getName());
        assertNull(manager.getEmail());
        assertNull(manager.getDepartment());
        assertNull(manager.getPhoneNumber());
        assertTrue(manager.getIsActive()); // Default should be true
        assertNull(manager.getCreatedAt());
        assertNull(manager.getUpdatedAt());
    }
}