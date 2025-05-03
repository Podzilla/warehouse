package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.PackagedOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackagedOrdersRepository extends JpaRepository<PackagedOrders, Long> {
    List<PackagedOrders> findByPackagerIdIsNull();
    List<PackagedOrders> findByPackagerId(Long packagerId);
    List<PackagedOrders> findByOrderId(Long orderId);
}
