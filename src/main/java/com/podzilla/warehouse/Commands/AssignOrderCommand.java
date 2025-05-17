package com.podzilla.warehouse.Commands;

import com.podzilla.warehouse.Models.AssignedOrders;
import com.podzilla.warehouse.Services.AssignedOrdersService;

import java.util.UUID;

public class AssignOrderCommand implements Command {
    private final AssignedOrdersService assignedOrdersService;
    private final UUID orderId;
    private final UUID assignerId;
    private final UUID courierId;

    private AssignedOrders assignedOrderResult;

    public AssignOrderCommand(AssignedOrdersService assignedOrdersService, UUID orderId, UUID assignerId, UUID courierId) {
        this.assignedOrdersService = assignedOrdersService;
        this.orderId = orderId;
        this.assignerId = assignerId;
        this.courierId = courierId;
    }

    @Override
    public void execute() {
        this.assignedOrderResult = assignedOrdersService.assignOrder(orderId, assignerId, courierId);
    }

    public AssignedOrders getAssignedOrderResult() {
        return assignedOrderResult;
    }
}