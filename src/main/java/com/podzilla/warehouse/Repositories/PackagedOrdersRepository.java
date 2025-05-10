package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.PackagedOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PackagedOrdersRepository extends JpaRepository<PackagedOrders, UUID> {
    List<PackagedOrders> findByPackagerIdIsNull();
    List<PackagedOrders> findByPackagerId(UUID packagerId);
    List<PackagedOrders> findByOrderId(UUID orderId);
}
