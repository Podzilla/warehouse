package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.Assigner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignmentsRepository extends JpaRepository<Assigner, Long> {
    Optional<Assigner> findByOrderId(Long orderId);
}
