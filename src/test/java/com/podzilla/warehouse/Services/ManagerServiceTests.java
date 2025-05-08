package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.Manager;
import com.podzilla.warehouse.Repositories.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ManagerServiceTests {

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private ManagerService managerService;

    private Manager testManager;
    private final Long MANAGER_ID = 1L;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Create test manager
        testManager = new Manager("Test Manager", "test@example.com", "Logistics");
        testManager.setId(MANAGER_ID);
        testManager.setPhoneNumber("123-456-7890");
        testManager.setIsActive(true);
    }

    @Test
    public void testCreateManagerWithPhone() {
        // Arrange
        when(managerRepository.save(any(Manager.class))).thenReturn(testManager);

        // Act
        Manager result = managerService.createManager("Test Manager", "test@example.com", "Logistics", "123-456-7890");

        // Assert
        assertNotNull(result);
        assertEquals(testManager.getName(), result.getName());
        assertEquals(testManager.getEmail(), result.getEmail());
        assertEquals(testManager.getDepartment(), result.getDepartment());
        assertEquals(testManager.getPhoneNumber(), result.getPhoneNumber());
        verify(managerRepository, times(1)).save(any(Manager.class));
    }

    @Test
    public void testCreateManagerWithoutPhone() {
        // Arrange
        Manager managerWithoutPhone = new Manager("Test Manager", "test@example.com", "Logistics");
        managerWithoutPhone.setId(MANAGER_ID);
        when(managerRepository.save(any(Manager.class))).thenReturn(managerWithoutPhone);

        // Act
        Manager result = managerService.createManager("Test Manager", "test@example.com", "Logistics");

        // Assert
        assertNotNull(result);
        assertEquals(managerWithoutPhone.getName(), result.getName());
        assertEquals(managerWithoutPhone.getEmail(), result.getEmail());
        assertEquals(managerWithoutPhone.getDepartment(), result.getDepartment());
        assertNull(result.getPhoneNumber());
        verify(managerRepository, times(1)).save(any(Manager.class));
    }

    @Test
    public void testGetAllManagers() {
        // Arrange
        List<Manager> managers = Arrays.asList(testManager, new Manager("Another Manager", "another@example.com", "Shipping"));
        when(managerRepository.findAll()).thenReturn(managers);

        // Act
        List<Manager> result = managerService.getAllManagers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(managerRepository, times(1)).findAll();
    }

    @Test
    public void testGetActiveManagers() {
        // Arrange
        List<Manager> activeManagers = Arrays.asList(testManager);
        when(managerRepository.findByIsActiveTrue()).thenReturn(activeManagers);

        // Act
        List<Manager> result = managerService.getActiveManagers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.getFirst().getIsActive());
        verify(managerRepository, times(1)).findByIsActiveTrue();
    }

    @Test
    public void testGetInactiveManagers() {
        // Arrange
        Manager inactiveManager = new Manager("Inactive", "inactive@example.com", "Warehouse");
        inactiveManager.setIsActive(false);
        List<Manager> inactiveManagers = Arrays.asList(inactiveManager);
        when(managerRepository.findByIsActiveFalse()).thenReturn(inactiveManagers);

        // Act
        List<Manager> result = managerService.getInactiveManagers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertFalse(result.getFirst().getIsActive());
        verify(managerRepository, times(1)).findByIsActiveFalse();
    }

    @Test
    public void testGetManagerById_Found() {
        // Arrange
        when(managerRepository.findById(MANAGER_ID)).thenReturn(Optional.of(testManager));

        // Act
        Optional<Manager> result = managerService.getManagerById(MANAGER_ID);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testManager.getId(), result.get().getId());
        verify(managerRepository, times(1)).findById(MANAGER_ID);
    }

    @Test
    public void testGetManagerById_NotFound() {
        // Arrange
        when(managerRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Manager> result = managerService.getManagerById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(managerRepository, times(1)).findById(999L);
    }

    @Test
    public void testGetManagerByEmail() {
        // Arrange
        when(managerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testManager));

        // Act
        Optional<Manager> result = managerService.getManagerByEmail("test@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        verify(managerRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    public void testGetManagersByDepartment() {
        // Arrange
        List<Manager> departmentManagers = Arrays.asList(testManager);
        when(managerRepository.findByDepartment("Logistics")).thenReturn(departmentManagers);

        // Act
        List<Manager> result = managerService.getManagersByDepartment("Logistics");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Logistics", result.getFirst().getDepartment());
        verify(managerRepository, times(1)).findByDepartment("Logistics");
    }

    @Test
    public void testUpdateManager_Found() {
        // Arrange
        Manager updatedDetails = new Manager();
        updatedDetails.setName("Updated Name");
        updatedDetails.setEmail("updated@example.com");

        Manager expectedManager = new Manager("Updated Name", "updated@example.com", "Logistics");
        expectedManager.setId(MANAGER_ID);
        expectedManager.setPhoneNumber("123-456-7890");
        expectedManager.setIsActive(true);

        when(managerRepository.findById(MANAGER_ID)).thenReturn(Optional.of(testManager));
        when(managerRepository.save(any(Manager.class))).thenReturn(expectedManager);

        // Act
        Optional<Manager> result = managerService.updateManager(MANAGER_ID, updatedDetails);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Updated Name", result.get().getName());
        assertEquals("updated@example.com", result.get().getEmail());
        verify(managerRepository, times(1)).findById(MANAGER_ID);
        verify(managerRepository, times(1)).save(any(Manager.class));
    }

    @Test
    public void testUpdateManager_NotFound() {
        // Arrange
        Manager updatedDetails = new Manager();
        updatedDetails.setName("Updated Name");

        when(managerRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Manager> result = managerService.updateManager(999L, updatedDetails);

        // Assert
        assertFalse(result.isPresent());
        verify(managerRepository, times(1)).findById(999L);
        verify(managerRepository, never()).save(any(Manager.class));
    }

    @Test
    public void testActivateManager() {
        // Arrange
        Manager inactiveManager = new Manager("Inactive", "inactive@example.com", "Warehouse");
        inactiveManager.setId(2L);
        inactiveManager.setIsActive(false);

        Manager activatedManager = new Manager("Inactive", "inactive@example.com", "Warehouse");
        activatedManager.setId(2L);
        activatedManager.setIsActive(true);

        when(managerRepository.findById(2L)).thenReturn(Optional.of(inactiveManager));
        when(managerRepository.save(any(Manager.class))).thenReturn(activatedManager);

        // Act
        Optional<Manager> result = managerService.activateManager(2L);

        // Assert
        assertTrue(result.isPresent());
        assertTrue(result.get().getIsActive());
        verify(managerRepository, times(1)).findById(2L);
        verify(managerRepository, times(1)).save(any(Manager.class));
    }

    @Test
    public void testDeactivateManager() {
        // Arrange
        when(managerRepository.findById(MANAGER_ID)).thenReturn(Optional.of(testManager));

        Manager deactivatedManager = new Manager("Test Manager", "test@example.com", "Logistics");
        deactivatedManager.setId(MANAGER_ID);
        deactivatedManager.setIsActive(false);

        when(managerRepository.save(any(Manager.class))).thenReturn(deactivatedManager);

        // Act
        Optional<Manager> result = managerService.deactivateManager(MANAGER_ID);

        // Assert
        assertTrue(result.isPresent());
        assertFalse(result.get().getIsActive());
        verify(managerRepository, times(1)).findById(MANAGER_ID);
        verify(managerRepository, times(1)).save(any(Manager.class));
    }

    @Test
    public void testDeleteManager_Success() {
        // Arrange
        when(managerRepository.existsById(MANAGER_ID)).thenReturn(true);
        doNothing().when(managerRepository).deleteById(MANAGER_ID);

        // Act
        boolean result = managerService.deleteManager(MANAGER_ID);

        // Assert
        assertTrue(result);
        verify(managerRepository, times(1)).existsById(MANAGER_ID);
        verify(managerRepository, times(1)).deleteById(MANAGER_ID);
    }

    @Test
    public void testDeleteManager_NotFound() {
        // Arrange
        when(managerRepository.existsById(999L)).thenReturn(false);

        // Act
        boolean result = managerService.deleteManager(999L);

        // Assert
        assertFalse(result);
        verify(managerRepository, times(1)).existsById(999L);
        verify(managerRepository, never()).deleteById(anyLong());
    }
}