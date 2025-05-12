package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findById(UUID id);
    Optional<Manager> findByEmail(String email);

    List<Manager> findByDepartment(String department);

    List<Manager> findByIsActiveTrue();

    List<Manager> findByIsActiveFalse();

    Boolean existsById(UUID managerId);

    void deleteById(UUID managerId);
}
