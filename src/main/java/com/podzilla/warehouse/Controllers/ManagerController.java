package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Models.Manager;
import com.podzilla.warehouse.Services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("warehouse/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @PostMapping("/create")
    public ResponseEntity<?> createManager(@RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            String email = (String) request.get("email");
            String department = (String) request.get("department");
            String phoneNumber = (String) request.get("phoneNumber");

            if (name == null || email == null || department == null) {
                return ResponseEntity.badRequest().body("Name, email, and department are required");
            }

            Manager manager;
            if (phoneNumber != null) {
                manager = managerService.createManager(name, email, department, phoneNumber);
            } else {
                manager = managerService.createManager(name, email, department);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(manager);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating manager: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Manager>> getAllManagers() {
        return ResponseEntity.ok(managerService.getAllManagers());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Manager>> getActiveManagers() {
        return ResponseEntity.ok(managerService.getActiveManagers());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<Manager>> getInactiveManagers() {
        return ResponseEntity.ok(managerService.getInactiveManagers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getManagerById(@PathVariable Long id) {
        Optional<Manager> manager = managerService.getManagerById(id);
        return manager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getManagerByEmail(@PathVariable String email) {
        Optional<Manager> manager = managerService.getManagerByEmail(email);
        return manager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Manager>> getManagersByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(managerService.getManagersByDepartment(department));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateManager(@PathVariable Long id, @RequestBody Manager managerDetails) {
        Optional<Manager> updatedManager = managerService.updateManager(id, managerDetails);
        return updatedManager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activateManager(@PathVariable Long id) {
        Optional<Manager> manager = managerService.activateManager(id);
        return manager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateManager(@PathVariable Long id) {
        Optional<Manager> manager = managerService.deactivateManager(id);
        return manager.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable Long id) {
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