package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.AssignedOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignedOrdersRepository extends JpaRepository<AssignedOrders, Long> {
    List<AssignedOrders> findByAssignerIdIsNull();
    List<AssignedOrders> findByAssignerId(Long assignerId);
    List<AssignedOrders> findByOrderId(Long orderId);
}