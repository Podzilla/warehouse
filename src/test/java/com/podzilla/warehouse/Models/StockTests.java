package com.podzilla.warehouse.Models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StockTests {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Arrange
        Stock stock = new Stock();
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        // Act
        stock.setId(id);
        stock.setName("Item A");
        stock.setQuantity(50);
        stock.setThreshold(10);
        stock.setCreatedAt(now);
        stock.setUpdatedAt(now);

        // Assert
        assertEquals(id, stock.getId());
        assertEquals("Item A", stock.getName());
        assertEquals(50, stock.getQuantity());
        assertEquals(10, stock.getThreshold());
        assertEquals(now, stock.getCreatedAt());
        assertEquals(now, stock.getUpdatedAt());
    }

    @Test
    void testConstructorWithNameAndQuantity() {
        // Arrange & Act
        Stock stock = new Stock("Item B", 100);

        // Assert
        assertEquals("Item B", stock.getName());
        assertEquals(100, stock.getQuantity());
        assertNull(stock.getThreshold());
    }

    @Test
    void testConstructorWithNameQuantityThreshold() {
        // Arrange & Act
        Stock stock = new Stock("Item C", 200, 20);

        // Assert
        assertEquals("Item C", stock.getName());
        assertEquals(200, stock.getQuantity());
        assertEquals(20, stock.getThreshold());
    }

    @Test
    void testDefaultTimestampsInitiallyNull() {
        // Arrange & Act
        Stock stock = new Stock();

        // Assert
        assertNull(stock.getCreatedAt());
        assertNull(stock.getUpdatedAt());
    }
}
