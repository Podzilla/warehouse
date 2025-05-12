package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.AssignedOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssignedOrdersRepository extends JpaRepository<AssignedOrders, UUID> {
    List<AssignedOrders> findByAssignerIdIsNull();

    List<AssignedOrders> findByAssignerId(UUID assignerId);

    List<AssignedOrders> findByOrderId(UUID orderId);
}