package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Models.PackagedOrders;
import com.podzilla.warehouse.Services.PackagedOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/package")
@Validated
public class PackagedOrdersController {

    @Autowired
    private PackagedOrdersService packagedOrdersService;

    //@PreAuthorize("hasAnyRole('MANAGER', 'ASSIGNER', 'PACKAGER')")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Page<PackagedOrders>> getByOrderId(@PathVariable UUID orderId,
                                                             @PageableDefault(size = 10) Pageable pageable) {
        Page<PackagedOrders> packagedOrders = packagedOrdersService.findByOrderId(orderId, pageable);
        return packagedOrders.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(packagedOrders);
    }

    //@PreAuthorize("hasAnyRole('MANAGER', 'ASSIGNER', 'PACKAGER')")
    @GetMapping("/packager/{packagerId}")
    public ResponseEntity<Page<PackagedOrders>> getByPackagerId(@PathVariable UUID packagerId,
                                                                @PageableDefault(size = 10) Pageable pageable) {
        Page<PackagedOrders> packagedOrders = packagedOrdersService.findByPackagerId(packagerId, pageable);
        return packagedOrders.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(packagedOrders);
    }

    //@PreAuthorize("hasAnyRole('MANAGER', 'ASSIGNER', 'PACKAGER')")
    @GetMapping("/unassigned")
    public ResponseEntity<Page<PackagedOrders>> getUnassignedOrders(@PageableDefault(size = 10) Pageable pageable) {
        Page<PackagedOrders> packagedOrders = packagedOrdersService.findByPackagerIdIsNull(pageable);
        return packagedOrders.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(packagedOrders);
    }

    //@PreAuthorize("hasRole('PACKAGER')")
    @PostMapping
    public ResponseEntity<Optional<PackagedOrders>> packageOrder(@RequestParam UUID orderId, @RequestParam UUID packagerId) {
        Optional<PackagedOrders> packagedOrder = packagedOrdersService.packageOrder(orderId, packagerId);
        return ResponseEntity.ok(packagedOrder);
    }
}
