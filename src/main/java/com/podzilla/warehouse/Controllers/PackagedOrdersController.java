package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Models.PackagedOrders;
import com.podzilla.warehouse.Services.PackagedOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("warehouse/package")
public class PackagedOrdersController {

    @Autowired
    private PackagedOrdersService packagedOrdersService;

    //@PreAuthorize("hasAnyRole('MANAGER', 'ASSIGNER', 'PACKAGER')")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PackagedOrders>> getByOrderId(@PathVariable Long orderId) {
        Optional<List<PackagedOrders>> packagedOrders = packagedOrdersService.findByOrderId(orderId);
        return packagedOrders.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/packager/{packagerId}")
    public ResponseEntity<List<PackagedOrders>> getByPackagerId(@PathVariable Long packagerId) {
        Optional<List<PackagedOrders>> packagedOrders = packagedOrdersService.findByPackagerId(packagerId);
        return packagedOrders.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //@PreAuthorize("hasAnyRole('MANAGER', 'ASSIGNER', 'PACKAGER')")
    @GetMapping("/unassigned")
    public ResponseEntity<List<PackagedOrders>> getUnassignedOrders() {
        Optional<List<PackagedOrders>> packagedOrders = packagedOrdersService.findByPackagerIdIsNull();
        return packagedOrders.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //@PreAuthorize("hasRole('PACKAGER')")
    @PostMapping
    public ResponseEntity<PackagedOrders> packageOrder(@RequestParam Long orderId, @RequestParam Long packagerId) {
        PackagedOrders packagedOrder = packagedOrdersService.packageOrder(orderId, packagerId);
        return ResponseEntity.ok(packagedOrder);
    }
}