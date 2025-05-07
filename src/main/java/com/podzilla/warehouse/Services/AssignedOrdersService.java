package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.AssignedOrders;
import com.podzilla.warehouse.Repositories.AssignedOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignedOrdersService {
    @Autowired
    private AssignedOrdersRepository assignedOrdersRepository;

    public Optional<List<AssignedOrders>> findByOrderId(Long orderId) {
        return Optional.ofNullable(assignedOrdersRepository.findByOrderId(orderId));
    }
    
    public Optional<List<AssignedOrders>> findByAssignerId(Long assignerId) {
        return Optional.ofNullable(assignedOrdersRepository.findByAssignerId(assignerId));
    }
    
    public Optional<List<AssignedOrders>> findByAssignerIdIsNull() {
        return Optional.ofNullable(assignedOrdersRepository.findByAssignerIdIsNull());
    }

    public AssignedOrders assignOrder(Long orderId, Long assignerId, Long courierId) {
        AssignedOrders assignment = new AssignedOrders(orderId, assignerId, courierId);
        return assignedOrdersRepository.save(assignment);
    }

}
