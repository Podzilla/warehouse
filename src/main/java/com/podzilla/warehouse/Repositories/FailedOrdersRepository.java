package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.FailedOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface FailedOrdersRepository extends JpaRepository<FailedOrders, UUID> {
    Page<FailedOrders> findByOrderId(UUID orderId, Pageable pageable);
    Page<FailedOrders> findByCourierId(UUID courierId, Pageable pageable);
    Page<FailedOrders> findByStatus(FailedOrders.Status status, Pageable pageable);
    Optional<FailedOrders> findByOrderIdAndCourierId(UUID orderId, UUID courierId);
}