package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Commands.AssignOrderCommand;
import com.podzilla.warehouse.Models.AssignedOrders;
import com.podzilla.warehouse.Services.AssignedOrdersService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/assign")
public class AssignerController {

    @Autowired
    private AssignedOrdersService assignerService;

    @Operation(summary = "Assign an order to a courier")
    @PostMapping("/order")
    public ResponseEntity<AssignedOrders> assignOrder(@RequestParam UUID orderId,
                                                      @RequestParam UUID assignerId,
                                                      @RequestParam UUID courierId) {
        log.info("Received assignment request: orderId={}, assignerId={}, courierId={}", orderId, assignerId, courierId);

        AssignOrderCommand command = new AssignOrderCommand(assignerService, orderId, assignerId, courierId);
        command.execute();

        AssignedOrders assigned = command.getAssignedOrderResult();
        return ResponseEntity.ok(assigned);
    }

    @Operation(summary = "Find assignments by order ID")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<AssignedOrders>> getByOrderId(@PathVariable UUID orderId) {
        log.info("Fetching assignments for orderId={}", orderId);
        Optional<List<AssignedOrders>> result = assignerService.findByOrderId(orderId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Find assignments by assigner ID")
    @GetMapping("/assigner/{assignerId}")
    public ResponseEntity<List<AssignedOrders>> getByAssignerId(@PathVariable UUID assignerId) {
        log.info("Fetching assignments by assignerId={}", assignerId);
        Optional<List<AssignedOrders>> result = assignerService.findByAssignerId(assignerId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Find unassigned orders")
    @GetMapping("/unassigned")
    public ResponseEntity<List<AssignedOrders>> getUnassignedOrders() {
        log.info("Fetching unassigned orders");
        Optional<List<AssignedOrders>> result = assignerService.findByAssignerIdIsNull();
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
