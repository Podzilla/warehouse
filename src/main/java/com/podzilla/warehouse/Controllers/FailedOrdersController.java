package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Models.FailedOrders;
import com.podzilla.warehouse.Services.FailedOrdersService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("warehouse/failed-orders")
@Validated
public class FailedOrdersController {

    @Autowired
    private FailedOrdersService failedOrdersService;

    @GetMapping
    public ResponseEntity<Page<FailedOrders>> getAllFailedOrders(@PageableDefault(size = 10) Pageable pageable) {
        Page<FailedOrders> failedOrders = failedOrdersService.findAll(pageable);
        return failedOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(failedOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FailedOrders> getFailedOrderById(@PathVariable UUID id) {
        Optional<FailedOrders> failedOrder = failedOrdersService.findById(id);
        return failedOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Page<FailedOrders>> getFailedOrdersByOrderId(
            @PathVariable UUID orderId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<FailedOrders> failedOrders = failedOrdersService.findByOrderId(orderId, pageable);
        return failedOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(failedOrders);
    }

    @GetMapping("/courier/{courierId}")
    public ResponseEntity<Page<FailedOrders>> getFailedOrdersByCourierId(
            @PathVariable UUID courierId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<FailedOrders> failedOrders = failedOrdersService.findByCourierId(courierId, pageable);
        return failedOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(failedOrders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<FailedOrders>> getFailedOrdersByStatus(
            @PathVariable FailedOrders.Status status,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<FailedOrders> failedOrders = failedOrdersService.findByStatus(status, pageable);
        return failedOrders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(failedOrders);
    }

    @PostMapping
    public ResponseEntity<FailedOrders> createFailedOrder(@RequestBody FailedOrders failedOrder) {
        FailedOrders savedFailedOrder = failedOrdersService.save(failedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFailedOrder);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FailedOrders> updateFailedOrderStatus(
            @PathVariable UUID id,
            @RequestBody StatusUpdateRequest statusUpdateRequest) {
        Optional<FailedOrders> updatedFailedOrder = failedOrdersService.updateStatus(id, statusUpdateRequest.getStatus());
        return updatedFailedOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFailedOrder(@PathVariable UUID id) {
        failedOrdersService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Setter
    @Getter
    public static class StatusUpdateRequest {
        private FailedOrders.Status status;

    }
}