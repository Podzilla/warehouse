package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Models.Packager;
import com.podzilla.warehouse.Services.PackagerService;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("warehouse/packagers")
public class PackagerController {

    private final PackagerService packagerService;

    public PackagerController(PackagerService packagerService) {
        this.packagerService = packagerService;
    }

    @Operation(summary = "Get all packagers (paginated)")
    @GetMapping
    public Page<Packager> getAllPackagers(@PageableDefault(size = 10) Pageable pageable) {
        log.info("Fetching all packagers with pagination: pageNumber={}, pageSize={}", pageable.getPageNumber(), pageable.getPageSize());
        return packagerService.getAllPackagers(pageable);
    }

    @Operation(summary = "Get a packager by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Packager> getPackagerById(@PathVariable Long id) {
        log.info("Fetching packager by id={}", id);
        return packagerService.getPackagerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new packager")
    @PostMapping
    public Optional<Packager> createPackager(@RequestBody Packager packager) {
        log.info("Creating new packager");
        return packagerService.createPackager(packager);
    }

    @Operation(summary = "Update an existing packager")
    @PutMapping("/{id}")
    public ResponseEntity<Optional<Packager>> updatePackager(@PathVariable Long id, @RequestBody Packager updated) {
        log.info("Updating packager with id={}", id);
        try {
            return ResponseEntity.ok(packagerService.updatePackager(id, updated));
        } catch (RuntimeException e) {
            log.warn("Failed to update packager: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a packager by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackager(@PathVariable Long id) {
        log.info("Deleting packager with id={}", id);
        packagerService.deletePackager(id);
        return ResponseEntity.noContent().build();
    }
}
