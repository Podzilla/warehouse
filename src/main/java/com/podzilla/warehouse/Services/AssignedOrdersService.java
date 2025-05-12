package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Events.EventFactory;
import com.podzilla.warehouse.Events.OrderAssignedEvent;
import com.podzilla.warehouse.Models.AssignedOrders;
import com.podzilla.warehouse.Repositories.AssignedOrdersRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssignedOrdersService {
    @Autowired
    private AssignedOrdersRepository assignedOrdersRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Optional<List<AssignedOrders>> findByOrderId(UUID orderId) {
        return Optional.ofNullable(assignedOrdersRepository.findByOrderId(orderId));
    }

    public Optional<List<AssignedOrders>> findByAssignerId(UUID assignerId) {
        return Optional.ofNullable(assignedOrdersRepository.findByAssignerId(assignerId));
    }

    public Optional<List<AssignedOrders>> findByAssignerIdIsNull() {
        return Optional.ofNullable(assignedOrdersRepository.findByAssignerIdIsNull());
    }

    public AssignedOrders assignOrder(UUID orderId, UUID taskId, UUID courierId, UUID userId) {
        AssignedOrders assignment = new AssignedOrders(orderId, taskId, courierId, LocalDateTime.now());
        assignedOrdersRepository.save(assignment);

        OrderAssignedEvent event = EventFactory.createOrderAssignedEvent(
                assignment.getAssignedAt(), orderId, taskId, courierId
        );

        rabbitTemplate.convertAndSend("analytics.exchange", "analytics.order.assigned", event);
        return assignment;
    }

}
