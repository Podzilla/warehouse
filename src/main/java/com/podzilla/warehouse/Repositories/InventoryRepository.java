package com.podzilla.warehouse.Repositories;

import com.podzilla.warehouse.Models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Manager, Long> {
}
