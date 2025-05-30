package com.podzilla.warehouse.Services;

import com.podzilla.mq.EventPublisher;
import com.podzilla.mq.EventsConstants;
import com.podzilla.mq.events.OrderAssignedToCourierEvent;
import com.podzilla.warehouse.Events.EventFactory;
import com.podzilla.warehouse.Models.AssignedOrders;
import com.podzilla.warehouse.Models.PackagedOrders;
import com.podzilla.warehouse.Repositories.AssignedOrdersRepository;
import com.podzilla.warehouse.Repositories.PackagedOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"assignedOrders"})
public class AssignedOrdersService {
    private final EventPublisher eventPublisher;

    @Autowired
    private AssignedOrdersRepository assignedOrdersRepository;
    private PackagedOrdersRepository packagedOrdersRepository;

    @Cacheable(key = "'orderId:' + #orderId")
    public Optional<List<AssignedOrders>> findByOrderId(UUID orderId) {
        return Optional.ofNullable(assignedOrdersRepository.findByOrderId(orderId));
    }

    @Cacheable(key = "'assignerId:' + #assignerId")
    public Optional<List<AssignedOrders>> findByAssignerId(UUID assignerId) {
        return Optional.ofNullable(assignedOrdersRepository.findByAssignerId(assignerId));
    }

    @Cacheable(key = "'unassigned'")
    public Optional<List<AssignedOrders>> findByAssignerIdIsNull() {
        return Optional.ofNullable(assignedOrdersRepository.findByAssignerIdIsNull());
    }

    @CachePut(key = "'orderId:' + #orderId")
    public AssignedOrders assignOrder(UUID orderId, UUID assignerId, UUID courierId) {
        AssignedOrders assignment = assignedOrdersRepository.findOneByOrderId(orderId);
        assignment.setAssignerId(assignerId);
        assignment.setCourierId(courierId);
        assignedOrdersRepository.save(assignment);

        OrderAssignedToCourierEvent event = 
                EventFactory.createOrderAssignedToCourierEvent(
                    orderId,
                    courierId, 
                    assignment.getTotalAmount(),
                    assignment.getOrderLatitude(),
                    assignment.getOrderLongitude(),
                    assignment.getSignature(),
                    assignment.getConfirmationType()
                );

        eventPublisher.publishEvent(EventsConstants.ORDER_ASSIGNED_TO_COURIER, event);
        return assignment;
    }

    // Optional: to clear all cache entries if needed
    @CacheEvict(allEntries = true)
    public void clearAssignedOrdersCache() {
        // Called manually if you need to flush everything
    }
}
