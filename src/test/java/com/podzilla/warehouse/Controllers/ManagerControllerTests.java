package com.podzilla.warehouse.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.podzilla.warehouse.Models.Manager;
import com.podzilla.warehouse.Services.ManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ManagerControllerTests {

    private MockMvc mockMvc;

    @Mock
    private ManagerService managerService;

    @InjectMocks
    private ManagerController managerController;

    private ObjectMapper objectMapper;
    private Manager testManager;
    private final Long MANAGER_ID = 1L;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();
        objectMapper = new ObjectMapper();

        // Create test manager
        testManager = new Manager("Test Manager", "test@example.com", "Logistics");
        testManager.setId(MANAGER_ID);
        testManager.setPhoneNumber("123-456-7890");
        testManager.setIsActive(true);
    }

    @Test
    public void testCreateManager_WithPhoneNumber() throws Exception {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Test Manager");
        requestBody.put("email", "test@example.com");
        requestBody.put("department", "Logistics");
        requestBody.put("phoneNumber", "123-456-7890");

        when(managerService.createManager(
                eq("Test Manager"),
                eq("test@example.com"),
                eq("Logistics"),
                eq("123-456-7890")
        )).thenReturn(testManager);

        // Act & Assert
        mockMvc.perform(post("/warehouse/manager/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Manager")))
                .andExpect(jsonPath("$.email", is("test@example.com")))
                .andExpect(jsonPath("$.department", is("Logistics")))
                .andExpect(jsonPath("$.phoneNumber", is("123-456-7890")))
                .andExpect(jsonPath("$.isActive", is(true)));

        verify(managerService, times(1)).createManager(
                eq("Test Manager"),
                eq("test@example.com"),
                eq("Logistics"),
                eq("123-456-7890"));
    }

    @Test
    public void testCreateManager_WithoutPhoneNumber() throws Exception {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Test Manager");
        requestBody.put("email", "test@example.com");
        requestBody.put("department", "Logistics");

        Manager managerWithoutPhone = new Manager("Test Manager", "test@example.com", "Logistics");
        managerWithoutPhone.setId(MANAGER_ID);
        managerWithoutPhone.setIsActive(true);

        when(managerService.createManager(
                eq("Test Manager"),
                eq("test@example.com"),
                eq("Logistics")
        )).thenReturn(managerWithoutPhone);

        // Act & Assert
        mockMvc.perform(post("/warehouse/manager/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Manager")))
                .andExpect(jsonPath("$.email", is("test@example.com")))
                .andExpect(jsonPath("$.department", is("Logistics")))
                .andExpect(jsonPath("$.phoneNumber").doesNotExist())
                .andExpect(jsonPath("$.isActive", is(true)));

        verify(managerService, times(1)).createManager(
                eq("Test Manager"),
                eq("test@example.com"),
                eq("Logistics"));
    }

    @Test
    public void testCreateManager_MissingRequiredFields() throws Exception {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Test Manager");
        // Missing email and department

        // Act & Assert
        mockMvc.perform(post("/warehouse/manager/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Name, email, and department are required"));

        verify(managerService, never()).createManager(anyString(), anyString(), anyString(), anyString());
        verify(managerService, never()).createManager(anyString(), anyString(), anyString());
    }

    @Test
    public void testGetAllManagers() throws Exception {
        // Arrange
        List<Manager> managers = Arrays.asList(
                testManager,
                new Manager("Another Manager", "another@example.com", "Shipping")
        );
        when(managerService.getAllManagers()).thenReturn(managers);

        // Act & Assert
        mockMvc.perform(get("/warehouse/manager/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Test Manager")))
                .andExpect(jsonPath("$[1].name", is("Another Manager")));

        verify(managerService, times(1)).getAllManagers();
    }

    @Test
    public void testGetActiveManagers() throws Exception {
        // Arrange
        List<Manager> activeManagers = Collections.singletonList(testManager);
        when(managerService.getActiveManagers()).thenReturn(activeManagers);

        // Act & Assert
        mockMvc.perform(get("/warehouse/manager/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Test Manager")))
                .andExpect(jsonPath("$[0].isActive", is(true)));

        verify(managerService, times(1)).getActiveManagers();
    }

    @Test
    public void testGetInactiveManagers() throws Exception {
        // Arrange
        Manager inactiveManager = new Manager("Inactive", "inactive@example.com", "Warehouse");
        inactiveManager.setId(2L);
        inactiveManager.setIsActive(false);
        List<Manager> inactiveManagers = Collections.singletonList(inactiveManager);
        when(managerService.getInactiveManagers()).thenReturn(inactiveManagers);

        // Act & Assert
        mockMvc.perform(get("/warehouse/manager/inactive"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Inactive")))
                .andExpect(jsonPath("$[0].isActive", is(false)));

        verify(managerService, times(1)).getInactiveManagers();
    }

    @Test
    public void testGetManagerById_Found() throws Exception {
        // Arrange
        when(managerService.getManagerById(MANAGER_ID)).thenReturn(Optional.of(testManager));

        // Act & Assert
        mockMvc.perform(get("/warehouse/manager/{id}", MANAGER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Manager")));

        verify(managerService, times(1)).getManagerById(MANAGER_ID);
    }

    @Test
    public void testGetManagerById_NotFound() throws Exception {
        // Arrange
        when(managerService.getManagerById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/warehouse/manager/{id}", 999L))
                .andExpect(status().isNotFound());

        verify(managerService, times(1)).getManagerById(999L);
    }

    @Test
    public void testGetManagerByEmail() throws Exception {
        // Arrange
        when(managerService.getManagerByEmail("test@example.com")).thenReturn(Optional.of(testManager));

        // Act & Assert
        mockMvc.perform(get("/warehouse/manager/email/{email}", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test@example.com")));

        verify(managerService, times(1)).getManagerByEmail("test@example.com");
    }

    @Test
    public void testGetManagersByDepartment() throws Exception {
        // Arrange
        List<Manager> departmentManagers = Collections.singletonList(testManager);
        when(managerService.getManagersByDepartment("Logistics")).thenReturn(departmentManagers);

        // Act & Assert
        mockMvc.perform(get("/warehouse/manager/department/{department}", "Logistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].department", is("Logistics")));

        verify(managerService, times(1)).getManagersByDepartment("Logistics");
    }

    @Test
    public void testActivateManager() throws Exception {
        // Arrange
        when(managerService.activateManager(MANAGER_ID)).thenReturn(Optional.of(testManager));

        // Act & Assert
        mockMvc.perform(put("/warehouse/manager/activate/{id}", MANAGER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isActive", is(true)));

        verify(managerService, times(1)).activateManager(MANAGER_ID);
    }

    @Test
    public void testDeactivateManager() throws Exception {
        // Arrange
        Manager deactivatedManager = new Manager("Test Manager", "test@example.com", "Logistics");
        deactivatedManager.setId(MANAGER_ID);
        deactivatedManager.setIsActive(false);

        when(managerService.deactivateManager(MANAGER_ID)).thenReturn(Optional.of(deactivatedManager));

        // Act & Assert
        mockMvc.perform(put("/warehouse/manager/deactivate/{id}", MANAGER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isActive", is(false)));

        verify(managerService, times(1)).deactivateManager(MANAGER_ID);
    }

    @Test
    public void testDeleteManager_Success() throws Exception {
        // Arrange
        when(managerService.deleteManager(MANAGER_ID)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/warehouse/manager/delete/{id}", MANAGER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Manager successfully deleted")));

        verify(managerService, times(1)).deleteManager(MANAGER_ID);
    }

    @Test
    public void testDeleteManager_NotFound() throws Exception {
        // Arrange
        when(managerService.deleteManager(999L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/warehouse/manager/delete/{id}", 999L))
                .andExpect(status().isNotFound());

        verify(managerService, times(1)).deleteManager(999L);
    }
}