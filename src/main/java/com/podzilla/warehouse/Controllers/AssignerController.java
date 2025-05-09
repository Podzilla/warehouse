package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Services.AssignedOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("warehouse/Assign")
public class AssignerController {

    @Autowired
    private AssignedOrdersService assignerService;

//    @PostMapping("/assignOrder")
//    public AssignedOrders assignOrder(@RequestBody AssignedOrders assignedOrders) {
//        return assignerService.assignOrder(assignedOrders.getOrderId(), assignedOrders.getCourierId());
//    }

//    @GetMapping("/order/{orderId}")
//    public AssignedOrders getByOrderId(@PathVariable Long orderId) {
//        Optional<AssignedOrders> assigner = assignerService.findByOrderId(orderId);
//        return assigner.orElse(null);
//    }
}
