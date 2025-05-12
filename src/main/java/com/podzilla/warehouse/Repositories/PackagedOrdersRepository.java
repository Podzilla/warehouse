package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.PackagedOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Repository
public interface PackagedOrdersRepository extends JpaRepository<PackagedOrders, Long> {
    Page<PackagedOrders> findByPackagerIdIsNull(Pageable pageable);
    Page<PackagedOrders> findByPackagerId(UUID packagerId, Pageable pageable);
    Page<PackagedOrders> findByOrderId(UUID orderId, Pageable pageable);
}
