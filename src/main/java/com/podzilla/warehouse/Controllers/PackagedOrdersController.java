package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Models.PackagedOrders;
import com.podzilla.warehouse.Services.PackagedOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/package")
public class PackagedOrdersController {

    @Autowired
    private PackagedOrdersService packagedOrdersService;

    @Operation(summary = "Get packaged orders by order ID")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PackagedOrders>> getByOrderId(@PathVariable UUID orderId) {
        log.info("Fetching packaged orders for orderId={}", orderId);
        Optional<List<PackagedOrders>> packagedOrders = packagedOrdersService.findByOrderId(orderId);
        return packagedOrders.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get packaged orders by packager ID")
    @GetMapping("/packager/{packagerId}")
    public ResponseEntity<List<PackagedOrders>> getByPackagerId(@PathVariable UUID packagerId) {
        log.info("Fetching packaged orders by packagerId={}", packagerId);
        Optional<List<PackagedOrders>> packagedOrders = packagedOrdersService.findByPackagerId(packagerId);
        return packagedOrders.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all unassigned packaged orders")
    @GetMapping("/unassigned")
    public ResponseEntity<List<PackagedOrders>> getUnassignedOrders() {
        log.info("Fetching unassigned packaged orders");
        Optional<List<PackagedOrders>> packagedOrders = packagedOrdersService.findByPackagerIdIsNull();
        return packagedOrders.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Package an order")
    @PostMapping
    public ResponseEntity<PackagedOrders> packageOrder(@RequestParam UUID orderId, @RequestParam UUID packagerId) {
        log.info("Packaging orderId={} by packagerId={}", orderId, packagerId);
        PackagedOrders packagedOrder = packagedOrdersService.packageOrder(orderId, packagerId);
        return ResponseEntity.ok(packagedOrder);
    }
}