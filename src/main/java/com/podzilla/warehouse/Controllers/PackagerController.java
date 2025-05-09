package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Models.Packager;
import com.podzilla.warehouse.Services.PackagerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("warehouse/packagers")
public class PackagerController {

    private final PackagerService packagerService;

    public PackagerController(PackagerService packagerService) {
        this.packagerService = packagerService;
    }

   // @PreAuthorize("hasAnyRole('MANAGER', 'ASSIGNER')")
    @GetMapping
    public Page<Packager> getAllPackagers(@PageableDefault(size = 10) Pageable pageable) {
        return packagerService.getAllPackagers(pageable);
    }

   //@PreAuthorize("hasAnyRole('MANAGER', 'ASSIGNER')")
    @GetMapping("/{id}")
    public ResponseEntity<Packager> getPackagerById(@PathVariable Long id) {
        return packagerService.getPackagerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   // @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public Packager createPackager(@RequestBody Packager packager) {
        return packagerService.createPackager(packager);
    }

   // @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Packager> updatePackager(@PathVariable Long id, @RequestBody Packager updated) {
        try {
            return ResponseEntity.ok(packagerService.updatePackager(id, updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

   // @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackager(@PathVariable Long id) {
        packagerService.deletePackager(id);
        return ResponseEntity.noContent().build();
    }
}
