package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Events.EventFactory;
import com.podzilla.warehouse.Events.OrderPackagingCompletedEvent;
import com.podzilla.warehouse.Models.PackagedOrders;
import com.podzilla.warehouse.Repositories.PackagedOrdersRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PackagedOrdersService {
    @Autowired
    private PackagedOrdersRepository packagedOrdersRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Optional<List<PackagedOrders>> findByOrderId(UUID orderId) {
        return Optional.ofNullable(packagedOrdersRepository.findByOrderId(orderId));
    }

    public Optional<List<PackagedOrders>> findByPackagerId(UUID packagerId) {
        return Optional.ofNullable(packagedOrdersRepository.findByPackagerId(packagerId));
    }

    public Optional<List<PackagedOrders>> findByPackagerIdIsNull() {
        return Optional.ofNullable(packagedOrdersRepository.findByPackagerIdIsNull());
    }

    public PackagedOrders packageOrder(UUID orderId, UUID packagerId) {
        LocalDateTime now = LocalDateTime.now();
        PackagedOrders packagedOrder = new PackagedOrders(orderId, packagerId, now);
        packagedOrdersRepository.save(packagedOrder);

        // Emit event to Analytics
        OrderPackagingCompletedEvent event = EventFactory.createOrderPackagingCompletedEvent(
                now,
                orderId,
                packagerId
        );

        rabbitTemplate.convertAndSend("analytics.exchange", "analytics.order.packaged", event);

        return packagedOrder;
    }
}
