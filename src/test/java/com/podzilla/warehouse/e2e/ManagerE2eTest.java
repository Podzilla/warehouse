package com.podzilla.warehouse.e2e;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * E2E tests for the Manager API.
 * These tests interact with a running instance of the application.
 * Make sure the application is running on localhost:8080 before running these tests.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManagerE2eTest extends E2eTestBase {

    private static UUID createdManagerId;
    private static final String MANAGER_ENDPOINT = "/manager";

    @Test
    @Order(1)
    public void testCreateManager() {
        // Prepare test data
        Map<String, Object> managerData = new HashMap<>();
        managerData.put("name", "E2E Test Manager");
        managerData.put("email", "e2e-test-manager@example.com");
        managerData.put("department", "E2E Testing");
        managerData.put("phoneNumber", "123-456-7890");

        // Send request
        ResponseEntity<Map<String, Object>> response = E2eTestUtils.post(
                restTemplate,
                BASE_URL,
                MANAGER_ENDPOINT + "/create",
                managerData,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        // Verify response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> manager = response.getBody();
        assertNotNull(manager.get("id"));
        assertEquals("E2E Test Manager", manager.get("name"));
        assertEquals("e2e-test-manager@example.com", manager.get("email"));
        assertEquals("E2E Testing", manager.get("department"));
        assertEquals("123-456-7890", manager.get("phoneNumber"));

        // Save the ID for later tests
        createdManagerId = UUID.fromString(manager.get("id").toString());
    }

    @Test
    @Order(2)
    public void testGetManagerById() {
        // Send request
        ResponseEntity<Map<String, Object>> response = E2eTestUtils.get(
                restTemplate,
                BASE_URL,
                MANAGER_ENDPOINT + "/" + createdManagerId,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> manager = response.getBody();
        assertEquals(createdManagerId.toString(), manager.get("id").toString());
        assertEquals("E2E Test Manager", manager.get("name"));
        assertEquals("e2e-test-manager@example.com", manager.get("email"));
        assertEquals("E2E Testing", manager.get("department"));
    }

    @Test
    @Order(3)
    public void testGetAllManagers() {
        // Send request
        ResponseEntity<List<Map<String, Object>>> response = E2eTestUtils.get(
                restTemplate,
                BASE_URL,
                MANAGER_ENDPOINT + "/all",
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @Order(4)
    public void testUpdateManager() {
        // Prepare test data
        Map<String, Object> managerData = new HashMap<>();
        managerData.put("id", createdManagerId.toString());
        managerData.put("name", "Updated E2E Test Manager");
        managerData.put("email", "updated-e2e-test-manager@example.com");
        managerData.put("department", "Updated E2E Testing");
        managerData.put("phoneNumber", "987-654-3210");
        managerData.put("active", true);

        // Send request
        ResponseEntity<Map<String, Object>> response = E2eTestUtils.put(
                restTemplate,
                BASE_URL,
                MANAGER_ENDPOINT + "/update/" + createdManagerId,
                managerData,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> manager = response.getBody();
        assertEquals(createdManagerId.toString(), manager.get("id").toString());
        assertEquals("Updated E2E Test Manager", manager.get("name"));
        assertEquals("updated-e2e-test-manager@example.com", manager.get("email"));
        assertEquals("Updated E2E Testing", manager.get("department"));
        assertEquals("987-654-3210", manager.get("phoneNumber"));
    }

    @Test
    @Order(5)
    public void testDeactivateManager() {
        // Send request
        ResponseEntity<Map<String, Object>> response = E2eTestUtils.put(
                restTemplate,
                BASE_URL,
                MANAGER_ENDPOINT + "/deactivate/" + createdManagerId,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> manager = response.getBody();
        assertEquals(createdManagerId.toString(), manager.get("id").toString());
        assertEquals(false, manager.get("active"));
    }

    @Test
    @Order(6)
    public void testActivateManager() {
        // Send request
        ResponseEntity<Map<String, Object>> response = E2eTestUtils.put(
                restTemplate,
                BASE_URL,
                MANAGER_ENDPOINT + "/activate/" + createdManagerId,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> manager = response.getBody();
        assertEquals(createdManagerId.toString(), manager.get("id").toString());
        assertEquals(true, manager.get("active"));
    }

    @Test
    @Order(7)
    public void testDeleteManager() {
        // Send request
        ResponseEntity<Map<String, Object>> response = E2eTestUtils.delete(
                restTemplate,
                BASE_URL,
                MANAGER_ENDPOINT + "/delete/" + createdManagerId,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Manager successfully deleted", response.getBody().get("message"));

        // Verify the manager is deleted
        ResponseEntity<Map<String, Object>> getResponse = E2eTestUtils.get(
                restTemplate,
                BASE_URL,
                MANAGER_ENDPOINT + "/" + createdManagerId,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}
