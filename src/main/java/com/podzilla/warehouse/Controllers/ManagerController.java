package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Models.Manager;
import com.podzilla.warehouse.Services.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("api/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @Operation(summary = "Create a new manager")
    @PostMapping("/create")
    public ResponseEntity<?> createManager(@RequestBody Map<String, Object> request) {
        try {
            log.info("Creating manager with request: {}", request);
            String name = (String) request.get("name");
            String email = (String) request.get("email");
            String department = (String) request.get("department");
            String phoneNumber = (String) request.get("phoneNumber");

            if (name == null || email == null || department == null) {
                return ResponseEntity.badRequest().body("Name, email, and department are required");
            }

            Manager manager = phoneNumber != null
                    ? managerService.createManager(name, email, department, phoneNumber)
                    : managerService.createManager(name, email, department);

            return ResponseEntity.status(HttpStatus.CREATED).body(manager);
        } catch (Exception e) {
            log.error("Error creating manager", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating manager: " + e.getMessage());
        }
    }

    @Operation(summary = "Get all managers")
    @GetMapping("/all")
    public ResponseEntity<List<Manager>> getAllManagers() {
        log.info("Fetching all managers");
        return ResponseEntity.ok(managerService.getAllManagers());
    }

    @Operation(summary = "Get all active managers")
    @GetMapping("/active")
    public ResponseEntity<List<Manager>> getActiveManagers() {
        log.info("Fetching active managers");
        return ResponseEntity.ok(managerService.getActiveManagers());
    }

    @Operation(summary = "Get all inactive managers")
    @GetMapping("/inactive")
    public ResponseEntity<List<Manager>> getInactiveManagers() {
        log.info("Fetching inactive managers");
        return ResponseEntity.ok(managerService.getInactiveManagers());
    }

    @Operation(summary = "Get manager by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getManagerById(@PathVariable UUID id) {
        log.info("Fetching manager by ID: {}", id);
        Optional<Manager> manager = managerService.getManagerById(id);
        return manager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get manager by email")
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getManagerByEmail(@PathVariable String email) {
        log.info("Fetching manager by email: {}", email);
        Optional<Manager> manager = managerService.getManagerByEmail(email);
        return manager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get managers by department")
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Manager>> getManagersByDepartment(@PathVariable String department) {
        log.info("Fetching managers by department: {}", department);
        return ResponseEntity.ok(managerService.getManagersByDepartment(department));
    }

    @Operation(summary = "Update manager by ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateManager(@PathVariable UUID id, @RequestBody Manager managerDetails) {
        log.info("Updating manager with ID: {}", id);
        Optional<Manager> updatedManager = managerService.updateManager(id, managerDetails);
        return updatedManager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Activate manager by ID")
    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activateManager(@PathVariable UUID id) {
        log.info("Activating manager with ID: {}", id);
        Optional<Manager> manager = managerService.activateManager(id);
        return manager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deactivate manager by ID")
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateManager(@PathVariable UUID id) {
        log.info("Deactivating manager with ID: {}", id);
        Optional<Manager> manager = managerService.deactivateManager(id);
        return manager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete manager by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable UUID id) {
        log.info("Deleting manager with ID: {}", id);
        boolean deleted = managerService.deleteManager(id);
        if (deleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Manager successfully deleted");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
