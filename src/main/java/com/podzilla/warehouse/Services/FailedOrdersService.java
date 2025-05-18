package com.podzilla.warehouse.Services;

import com.podzilla.warehouse.Models.FailedOrders;
import com.podzilla.warehouse.Repositories.FailedOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"failedOrders"})
public class FailedOrdersService {

    @Autowired
    private FailedOrdersRepository failedOrdersRepository;

    @Cacheable(value = "failedOrdersById", key = "#id.toString()")
    public Optional<FailedOrders> findById(UUID id) {
        return failedOrdersRepository.findById(id);
    }

    @Cacheable(value = "failedOrdersByOrderId", key = "#orderId.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<FailedOrders> findByOrderId(UUID orderId, Pageable pageable) {
        return failedOrdersRepository.findByOrderId(orderId, pageable);
    }

    @Cacheable(value = "failedOrdersByCourierId", key = "#courierId.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<FailedOrders> findByCourierId(UUID courierId, Pageable pageable) {
        return failedOrdersRepository.findByCourierId(courierId, pageable);
    }

    @Cacheable(value = "failedOrdersByStatus", key = "#status.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<FailedOrders> findByStatus(FailedOrders.Status status, Pageable pageable) {
        return failedOrdersRepository.findByStatus(status, pageable);
    }

    @Cacheable(value = "allFailedOrders", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<FailedOrders> findAll(Pageable pageable) {
        return failedOrdersRepository.findAll(pageable);
    }

    @CachePut(value = "failedOrdersById", key = "#result.id.toString()")
    public FailedOrders save(FailedOrders failedOrder) {
        return failedOrdersRepository.save(failedOrder);
    }

    @CachePut(value = "failedOrdersById", key = "#id.toString()")
    public Optional<FailedOrders> updateStatus(UUID id, FailedOrders.Status status) {
        Optional<FailedOrders> failedOrderOptional = failedOrdersRepository.findById(id);
        return failedOrderOptional.map(failedOrder -> {
            failedOrder.setStatus(status);
            return failedOrdersRepository.save(failedOrder);
        });
    }

    @CacheEvict(value = "failedOrdersById", key = "#id.toString()")
    public void deleteById(UUID id) {
        failedOrdersRepository.deleteById(id);
    }

    @CacheEvict(value = {
            "failedOrdersById",
            "failedOrdersByOrderId",
            "failedOrdersByCourierId",
            "failedOrdersByStatus",
            "allFailedOrders"
    }, allEntries = true)
    public void clearCache() {
        // No-op, just used to manually trigger cache eviction if needed
    }
}