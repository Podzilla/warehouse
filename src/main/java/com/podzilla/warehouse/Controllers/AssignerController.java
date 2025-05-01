package com.podzilla.warehouse.Controllers;

import com.podzilla.warehouse.Models.Assigner;
import com.podzilla.warehouse.Repositories.AssignmentsRepository;
import com.podzilla.warehouse.Services.AssignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Assign")
public class AssignerController {

    @Autowired
    private AssignerService assignerService;

    @PostMapping("/assignOrder")
    public Assigner assignOrder(@RequestBody Assigner assigner) {
        return assignerService.assignOrder(assigner.getOrderId(), assigner.getCourierId());
    }

    @GetMapping("/order/{orderId}")
    public Assigner getByOrderId(@PathVariable Long orderId) {
        Optional<Assigner> assigner = assignerService.findByOrderId(orderId);
        return assigner.orElse(null);
    }

}
