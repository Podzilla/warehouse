package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Models.Packager;
import com.podzilla.warehouse.Services.PackagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PackagerControllerTests {

    private PackagerService packagerService;
    private PackagerController controller;

    @BeforeEach
    void setUp() {
        packagerService = mock(PackagerService.class);
        controller = new PackagerController(packagerService);
    }

    @Test
    void testGetAllPackagers() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Packager> list = List.of(new Packager("John", true), new Packager("Jane", false));
        Page<Packager> page = new PageImpl<>(list);
        when(packagerService.getAllPackagers(pageable)).thenReturn(page);

        // Act
        Page<Packager> result = controller.getAllPackagers(pageable);

        // Assert
        assertEquals(2, result.getTotalElements());
        verify(packagerService, times(1)).getAllPackagers(pageable);
    }

    @Test
    void testGetPackagerById_Found() {
        // Arrange
        Packager packager = new Packager("Sam", true);
        when(packagerService.getPackagerById(1L)).thenReturn(Optional.of(packager));

        // Act
        ResponseEntity<Packager> response = controller.getPackagerById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sam", response.getBody().getName());
    }

    @Test
    void testGetPackagerById_NotFound() {
        // Arrange
        when(packagerService.getPackagerById(99L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Packager> response = controller.getPackagerById(99L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }

//    @Test
//    void testCreatePackager() {
//        // Arrange
//        Packager packager = new Packager("Alex", true);
//        when(packagerService.createPackager(packager)).thenReturn(Optional.of(packager));
//
//        // Act
//        Optional<Packager> created = controller.createPackager(packager);
//
//        // Assert
//        assertEquals("Alex", created.getName());
//        assertTrue(created.isActive());
//    }
//
//    @Test
//    void testUpdatePackager_Success() {
//        // Arrange
//        Packager updated = new Packager("Updated", true);
//        when(packagerService.updatePackager(1L, updated)).thenReturn(Optional.of(updated));
//
//        // Act
//        ResponseEntity<Optional<Packager>> response = controller.updatePackager(1L, updated);
//
//        // Assert
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("Updated", response.getBody().getName());
//    }

    @Test
    void testUpdatePackager_NotFound() {
        // Arrange
        Packager updated = new Packager("Missing", true);
        when(packagerService.updatePackager(2L, updated)).thenThrow(new RuntimeException("Not found"));

        // Act
        ResponseEntity<Optional<Packager>> response = controller.updatePackager(2L, updated);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeletePackager() {
        // Arrange
        doNothing().when(packagerService).deletePackager(3L);

        // Act
        ResponseEntity<Void> response = controller.deletePackager(3L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(packagerService).deletePackager(3L);
    }
}
