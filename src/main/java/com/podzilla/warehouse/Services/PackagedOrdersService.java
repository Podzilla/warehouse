package com.podzilla.warehouse.Services;

import com.podzilla.mq.EventPublisher;
import com.podzilla.mq.EventsConstants;
import com.podzilla.mq.events.OrderPackagedEvent;
import com.podzilla.warehouse.Events.EventFactory;
import com.podzilla.warehouse.Models.AssignedOrders;
import com.podzilla.warehouse.Models.PackagedOrders;
import com.podzilla.warehouse.Repositories.AssignedOrdersRepository;
import com.podzilla.warehouse.Repositories.PackagedOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"packagedOrders"})
public class PackagedOrdersService {
    private final EventPublisher eventPublisher;
    @Autowired
    private final PackagedOrdersRepository packagedOrdersRepository;

    private final AssignedOrdersRepository assignedOrdersRepository;

    @Cacheable(value = "packagedOrdersByOrderId", key = "#orderId.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<PackagedOrders> findByOrderId(UUID orderId, Pageable pageable) {
        return packagedOrdersRepository.findByOrderId(orderId, pageable);
    }

    @Cacheable(value = "packagedOrdersByPackagerId", key = "#packagerId.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<PackagedOrders> findByPackagerId(UUID packagerId, Pageable pageable) {
        return packagedOrdersRepository.findByPackagerId(packagerId, pageable);
    }

    @Cacheable(value = "packagedOrdersUnassigned", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<PackagedOrders> findByPackagerIdIsNull(Pageable pageable) {
        return packagedOrdersRepository.findByPackagerIdIsNull(pageable);
    }

    @CachePut(value = "packagedOrdersByPackagerId", key = "#packagerId.toString() + '-0-10'")
    public Optional<PackagedOrders> packageOrder(UUID orderId,UUID packagerId) {
        PackagedOrders packagedOrder = packagedOrdersRepository.findOneByOrderId(orderId).get();
        packagedOrder.setPackagerId(packagerId);
        OrderPackagedEvent event = EventFactory.createOrderPackagedEvent(orderId);
        eventPublisher.publishEvent(EventsConstants.ORDER_PACKAGED, event);

        AssignedOrders assignment = AssignedOrders.builder()
                .orderId(orderId)
                .items(packagedOrder.getItems())
                .deliveryAddress(packagedOrder.getDeliveryAddress())
                .totalAmount(packagedOrder.getTotalAmount())
                .orderLatitude(packagedOrder.getOrderLatitude())
                .orderLongitude(packagedOrder.getOrderLongitude())
                .confirmationType(packagedOrder.getConfirmationType())
                .signature(packagedOrder.getSignature())
                .build();

        assignedOrdersRepository.save(assignment);

        return Optional.of(packagedOrdersRepository.save(packagedOrder));
    }

    @CacheEvict(value = {
            "packagedOrdersByOrderId",
            "packagedOrdersByPackagerId",
            "packagedOrdersUnassigned"
    }, allEntries = true)
    public void clearCache() {
        // No-op, just used to manually trigger cache eviction if needed
    }
}

